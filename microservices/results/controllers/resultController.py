from repositories.resultRepository import ResultRepository
from repositories.tableRepository import TableRepository
from repositories.candidateRepository import CandidateRepository
from repositories.partyRepository import PartyRepository
from controllers.tableController import TableController
from models.result import Result
from models.table import Table
from models.candidate import Candidate


class ResultController():
    def __init__(self):
        self.resultRepository = ResultRepository()
        self.tableRepository = TableRepository()
        self.candidateRepository = CandidateRepository()
        self.partyRepository = PartyRepository()
        self.tableController = TableController()
        print("Creando controlador para resultado")


    def findAll(self):
        print("Listar todos los resultados")
        return self.resultRepository.findAll()


    def create(self, info, id_table, id_candidate):
        print("Creando un resultado")
        new_result = Result(info)
        table = Table(self.tableRepository.findById(id_table))
        candidate = Candidate(self.candidateRepository.findById(id_candidate))
        new_result.table = table
        new_result.candidate = candidate
        self.tableController.updateTableVotes(id_table, info["votes"])
        return self.resultRepository.save(new_result)


    def findById(self, id):
        print("Listar un resultado por id")
        return self.resultRepository.findById(id)


    def update(self, info, id_result, id_table, id_candidate):
        print("Actualizando un resultado")
        old_result = Result(self.resultRepository.findById(id_result))
        table = Table(self.tableRepository.findById(id_table))
        candidate = Candidate(self.candidateRepository.findById(id_candidate))
        old_result.table = table
        old_result.candidate = candidate
        old_result.votes = info["votes"]
        self.tableController.updateTableVotes(id_table, info["votes"])
        return self.resultRepository.save(old_result)


    def delete(self, id):
        print("Eliminando un resultado")
        return self.resultRepository.delete(id)


    # METODO PARA OBTENER TODOS LOS RESULTADOS POR MESA
    def orderTableReport(self):
        results = self.findAll()
        for i in range(len(results)):
            lowesr_value = i
            for j in range(i+1, len(results)):
                if int(results[j]["votes"]) < int(results[lowesr_value]["votes"]):
                    lowesr_value = j
            results[i]["votes"], results[lowesr_value]["votes"] = results[lowesr_value]["votes"], results[i]["votes"]
            results[i]["candidate"], results[lowesr_value]["candidate"] = results[lowesr_value]["candidate"], results[i]["candidate"]
        return results


    def partiesReport(self):
        results_array = self.resultRepository.findAll()
        parties_array = self.partyRepository.findAll()
        name_per_party = []
        votes_per_party = []
        total_votes = 0

        i = 0
        for party in parties_array:
            first = True
            for result in results_array:
                if result["candidate"]["party"] == party:
                    if (first):
                        name_per_party.insert(i, party["name"])
                        votes_per_party.insert(i, result["votes"])
                        i = i + 1
                    else:
                        votes_per_party[i - 1] += result["votes"]
                    total_votes += result["votes"]
                    first = False

        reports = self.formatReport(name_per_party, votes_per_party)
        reports = self.largestToSmallest(reports)
        return reports


    def formatReport(self, name_per_party, votes_per_party):
        reports = []
        for i in range(0, len(name_per_party)):
            reports.insert(i, {
                "party": name_per_party[i],
                "votes": votes_per_party[i]
            })
        return reports


    def largestToSmallest(self, reports):
        for i in range(len(reports)):
            lowesr_value = i
            for j in range(i+1, len(reports)):
                if int(reports[j]["votes"]) > int(reports[lowesr_value]["votes"]):
                    lowesr_value = j
            reports[i]["votes"], reports[lowesr_value]["votes"] = reports[lowesr_value]["votes"], reports[i]["votes"]
            reports[i]["party"], reports[lowesr_value]["party"] = reports[lowesr_value]["party"], reports[i]["party"]
        return reports