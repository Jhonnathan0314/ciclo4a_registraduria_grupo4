from repositories.tableRepository import TableRepository
from repositories.candidateRepository import CandidateRepository
from repositories.resultRepository import ResultRepository
from models.table import Table
from models.result import Result
from models.candidate import Candidate


class TableController():
    def __init__(self):
        self.tableRepository = TableRepository()
        self.candidateRepository = CandidateRepository()
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
        results_table = self.resultRepository.findByTable(id_table)
        results_party = self.resultRepository.findByParty(id_party)
        candidate = Candidate(self.candidateRepository.findById(id_candidate))
        for table in results_table:
            for party in results_party:
                pass
        return candidate