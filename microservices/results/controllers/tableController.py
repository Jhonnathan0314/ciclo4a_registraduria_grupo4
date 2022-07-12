from repositories.tableRepository import TableRepository
from models.table import Table


class TableController():
    def __init__(self):
        self.tableRepository = TableRepository()
        print("Creando controlador para mesa")


    def findAll(self):
        print("Listar todas las mesas")
        return self.tableRepository.findAll()


    def create(self, info):
        print("Creando una mesa")
        new_table = Table(info)
        return self.tableRepository.save(new_table)


    def findById(self, id):
        print("Listar una mesa por id")
        return self.tableRepository.findById(id)


    def update(self, id, info):
        print("Actualizando una mesa")
        old_table = Table(self.tableRepository.findById(id))
        old_table.numberIds = info["numberIds"]
        return self.tableRepository.save(old_table)


    def delete(self, id):
        print("Eliminando una mesa")
        return self.tableRepository.delete(id)