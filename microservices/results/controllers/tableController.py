from repositories.tableRepository import TableRepository
from repositories.candidateRepository import CandidateRepository
from repositories.partyRepository import PartyRepository
from repositories.resultRepository import ResultRepository
from models.table import Table
from models.party import Party
from models.candidate import Candidate


class TableController():
    def __init__(self):
        self.tableRepository = TableRepository()
        self.candidateRepository = CandidateRepository()
        self.partyRepository = PartyRepository()
        self.resultRepository = ResultRepository()
        print("Creando controlador para mesa")


    def findAll(self):
        print("Listar todas las mesas")
        return self.tableRepository.findAll()


    def create(self, info):
        print("Creando una mesa")
        new_table = Table(info)
        new_table.totalVotes = 0
        return self.tableRepository.save(new_table)


    def findById(self, id):
        print("Listar una mesa por id")
        return self.tableRepository.findById(id)


    def update(self, id, info):
        print("Actualizando una mesa")
        old_table = Table(self.tableRepository.findById(id))
        old_table.numberIds = info["numberIds"]
        old_table.totalVotes = info["totalVotes"]
        return self.tableRepository.save(old_table)


    def delete(self, id):
        print("Eliminando una mesa")
        return self.tableRepository.delete(id)


    def findMaxNumVotes(self):
        return self.tableRepository.findMaxNumVotes()


    def findReportByTable(self, id_table):
        print()
        results = self.resultRepository.findByTable(id_table)
        total_votes = 0
        for votes in results:
            total_votes += votes["votes"]
        table = Table(self.tableRepository.findById(id_table))
        table.totalVotes = total_votes
        return self.tableRepository.save(table)


    def findReportVotes(self, id_table, id_candidate, id_party):
        table = Table(self.findById(id_table))
        party = Party(self.partyRepository.findById(id_party))
        candidate = Candidate(self.candidateRepository.findById(id_candidate))
        result_array = self.resultRepository.findByTableAndParty(id_table, id_party)

        if(len(result_array) != 0 and candidate.party["_id"] == party._id):
            result = result_array[0]
            report = {
                "party": party.name,
                "candidate": candidate.name,
                "table": table._id,
                "votes": result["votes"]
            }
        else:
            report = {
                "message": "No hay resultados"
            }
        return report


    def findReportVotesPartyInTable(self, id_table, id_party):
        table = Table(self.tableRepository.findById(id_table))
        party = Party(self.partyRepository.findById(id_party))
        result_array = self.resultRepository.findByTableAndParty(id_table, id_party)

        if(len(result_array) != 0):
            result = result_array[0]
            report = {
                "party": party.name,
                "table": table._id,
                "totalVotes": result["votes"]
            }
        else:
            report = {
                "message": "No hay resultados"
            }
        return report


    def findReportPercentage(self):
        results_array = self.resultRepository.findAll()
        parties_array = self.partyRepository.findAll()
        name_per_party = []
        votes_per_party = []
        total_votes = 0

        i= 0
        first = True
        for party in parties_array:
            first = True
            for result in results_array:
                if result["party"] == party:
                    if(first):
                        name_per_party.insert(i, party["name"])
                        votes_per_party.insert(i, result["votes"])
                        i = i + 1
                    else:
                        votes_per_party[i-1] += result["votes"]
                    total_votes += result["votes"]
                    first = False

        reports = []
        for i in range(0, len(name_per_party)):
            reports.insert(i, {
                "party": name_per_party[i],
                "votes": votes_per_party[i],
                "percentage": "{:.2f}".format(((votes_per_party[i]/total_votes)*100)) + "%"
            })
        return reports