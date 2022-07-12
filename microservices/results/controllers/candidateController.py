from repositories.candidateRepository import CandidateRepository
from repositories.partyRepository import PartyRepository
from models.candidate import Candidate
from models.party import Party


class CandidateController():
    def __init__(self):
        self.candidateRepository = CandidateRepository()
        self.partyRepository = PartyRepository()
        print("Creando controlador para candidato")


    def findAll(self):
        print("Listar todos los candidatos")
        return self.candidateRepository.findAll()


    def create(self, info, id_party):
        print("Creando un candidato")
        new_candidate = Candidate(info)
        party = Party(self.partyRepository.findById(id_party))
        new_candidate.party = party
        return self.candidateRepository.save(new_candidate)


    def findById(self, id):
        print("Listar un candidato por id")
        return self.candidateRepository.findById(id)


    def update(self, info, id_candidate, id_party):
        print("Actualizando un candidato")
        old_candidate = Candidate(self.candidateRepository.findById(id_candidate))
        party = Party(self.partyRepository.findById(id_party))
        old_candidate.idCard = info["idCard"]
        old_candidate.resolutionNumber = info["resolutionNumber"]
        old_candidate.name = info["name"]
        old_candidate.lastname = info["lastname"]
        old_candidate.party = party
        return self.candidateRepository.save(old_candidate)


    def delete(self, id):
        print("Eliminando un candidato")
        return self.candidateRepository.delete(id)