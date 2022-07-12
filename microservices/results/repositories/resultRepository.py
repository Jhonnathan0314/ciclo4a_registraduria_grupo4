from models.result import Result
from repositories.interfaceRepository import InterfaceRepository
from bson import ObjectId

class ResultRepository(InterfaceRepository[Result]):
    # METODO PARA OBTENER TODOS LOS RESULTADOS POR MESA
    def findByTable(self, id_table):
        query = {"table.$id": ObjectId(id_table)}
        return self.query(query)
    

    # METODO PARA OBTENER TODOS LOS RESULTADOS POR MESA
    def findByParty(self, id_party):
        query = {"party.$id": ObjectId(id_party)}
        return self.query(query)