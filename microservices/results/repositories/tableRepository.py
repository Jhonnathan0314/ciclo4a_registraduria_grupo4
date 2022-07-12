from models.table import Table
from repositories.interfaceRepository import InterfaceRepository
from bson import ObjectId

class TableRepository(InterfaceRepository[Table]):
    def findMaxNumVotes(self):
        query = {
            "$group": {
                "_id": "$table",
                "max": {
                    "$max": "$totalVotes"
                },
                "doc": {
                    "$first": "$$ROOT"
                }
            }
        }
        pipeline = [query]
        return self.queryAggregation(pipeline)