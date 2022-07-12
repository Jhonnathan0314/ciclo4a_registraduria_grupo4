from repositories.resultRepository import ResultRepository
from repositories.tableRepository import TableRepository
from repositories.partyRepository import PartyRepository
from models.result import Result
from models.table import Table
from models.party import Party


class ResultController():
    def __init__(self):
        self.resultRepository = ResultRepository()
        self.tableRepository = TableRepository()
        self.partyRepository = PartyRepository()
        print("Creando controlador para resultado")


    def findAll(self):
        print("Listar todos los resultados")
        return self.resultRepository.findAll()


    def create(self, info, id_table, id_party):
        print("Creando un resultado")
        new_result = Result(info)
        table = Table(self.tableRepository.findById(id_table))
        party = Party(self.partyRepository.findById(id_party))
        new_result.table = table
        new_result.party = party
        return self.resultRepository.save(new_result)


    def findById(self, id):
        print("Listar un resultado por id")
        return self.resultRepository.findById(id)


    def update(self, info, id_result, id_table, id_party):
        print("Actualizando un resultado")
        old_result = Result(self.resultRepository.findById(id_result))
        table = Table(self.tableRepository.findById(id_table))
        party = Party(self.partyRepository.findById(id_party))
        old_result.table = table
        old_result.party = party
        return self.resultRepository.save(old_result)


    def delete(self, id):
        print("Eliminando un resultado")
        return self.resultRepository.delete(id)