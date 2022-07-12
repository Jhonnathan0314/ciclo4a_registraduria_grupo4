from repositories.partyRepository import PartyRepository
from models.party import Party


class PartyController():
    def __init__(self):
        self.partyRepository = PartyRepository()
        print("Creando controlador para partido")


    def findAll(self):
        print("Listar todos los partidos")
        return self.partyRepository.findAll()


    def create(self, info):
        print("Creando un partido")
        new_party = Party(info)
        return self.partyRepository.save(new_party)


    def findById(self, id):
        print("Listar un partido por id")
        return self.partyRepository.findById(id)


    def update(self, id, info):
        print("Actualizando un partido")
        old_party = Party(self.partyRepository.findById(id))
        old_party.name = info["name"]
        old_party.motto = info["motto"]
        return self.partyRepository.save(old_party)


    def delete(self, id):
        print("Eliminando un partido")
        return self.partyRepository.delete(id)