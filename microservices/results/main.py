from builtins import str

from flask import Flask
from flask import jsonify
from flask import request
from flask_cors import CORS
import json
# waitress es el servidor que usa flask
from waitress import serve

from controllers.tableController import TableController
from controllers.candidateController import CandidateController
from controllers.partyController import PartyController
from controllers.resultController import ResultController

#importaciones para conexion mongo
import pymongo
import certifi


#Variables
app = Flask(__name__)
cors = CORS(app)
table_controller = TableController()
candidate_controller = CandidateController()
party_controller = PartyController()
result_controller = ResultController()

#variables para conexiona la bd
ca = certifi.where()
client = pymongo.MongoClient("mongodb+srv://jonatan314:jonatan@cluster0.ub195.mongodb.net/?retryWrites=true&w=majority", tlsCAFile=ca)
db = client.test
print(db)

result_db = client["bd-results-backend"]
print(result_db.list_collection_names())


#Funciones
def load_file_config():
    with open("config.json") as f:
        data = json.load(f)
    return data


"""
----------- TABLE ENDPOINTS -----------
"""
@app.route("/table", methods=["GET"])
def get_tables():
    response = table_controller.findAll()
    return jsonify(response)


@app.route("/table/<string:id>", methods=["GET"])
def get_table(id):
    response = table_controller.findById(id)
    return jsonify(response)


@app.route("/table", methods=["POST"])
def create_table():
    info = request.get_json()
    response = table_controller.create(info)
    return jsonify(response)


@app.route("/table/<string:id>", methods=["PUT"])
def update_table(id):
    info = request.get_json()
    response = table_controller.update(id, info)
    return jsonify(response)


@app.route("/table/<string:id>", methods=["DELETE"])
def delete_table(id):
    response = table_controller.delete(id)
    return jsonify(response)


"""
----------- CANDIDATE ENDPOINTS -----------
"""
@app.route("/candidate", methods=["GET"])
def get_candidates():
    response = candidate_controller.findAll()
    return jsonify(response)


@app.route("/candidate/<string:id>", methods=["GET"])
def get_candidate(id):
    response = candidate_controller.findById(id)
    return jsonify(response)


@app.route("/candidate/party/<string:id_party>", methods=["POST"])
def create_candidate(id_party):
    info = request.get_json()
    response = candidate_controller.create(info, id_party)
    return jsonify(response)


@app.route("/candidate/<string:id_candidate>/party/<string:id_party>", methods=["PUT"])
def update_candidate(id_candidate, id_party):
    info = request.get_json()
    response = candidate_controller.update(info, id_candidate, id_party)
    return jsonify(response)


@app.route("/candidate/<string:id>", methods=["DELETE"])
def delete_candidate(id):
    response = candidate_controller.delete(id)
    return jsonify(response)


"""
----------- PARTY ENDPOINTS -----------
"""
@app.route("/party", methods=["GET"])
def get_parties():
    response = party_controller.findAll()
    return jsonify(response)


@app.route("/party/<string:id>", methods=["GET"])
def get_party(id):
    response = party_controller.findById(id)
    return jsonify(response)


@app.route("/party", methods=["POST"])
def create_party():
    info = request.get_json()
    response = party_controller.create(info)
    return jsonify(response)


@app.route("/party/<string:id>", methods=["PUT"])
def update_party(id):
    info = request.get_json()
    response = party_controller.update(id, info)
    return jsonify(response)


@app.route("/party/<string:id>", methods=["DELETE"])
def delete_party(id):
    response = party_controller.delete(id)
    return jsonify(response)


"""
----------- RESULT ENDPOINTS -----------
"""
@app.route("/result", methods=["GET"])
def get_results():
    response = result_controller.findAll()
    return jsonify(response)


@app.route("/result/<string:id>", methods=["GET"])
def get_result(id):
    response = result_controller.findById(id)
    return jsonify(response)


@app.route("/result/table/<string:id_table>/party/<string:id_party>", methods=["POST"])
def create_result(id_table, id_party):
    info = request.get_json()
    response = result_controller.create(info, id_table, id_party)
    return jsonify(response)


@app.route("/result/<string:id_result>/table/<string:id_table>/party/<string:id_party>", methods=["PUT"])
def update_result(id_result, id_table, id_party):
    info = request.get_json()
    response = result_controller.update(info, id_result, id_table, id_party)
    return jsonify(response)


@app.route("/result/<string:id>", methods=["DELETE"])
def delete_result(id):
    response = result_controller.delete(id)
    return jsonify(response)


if __name__ == '__main__':
    dataConfig = load_file_config()
    url = "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"])
    print("Server running in: " + url)
    serve(app, host=dataConfig["url-backend"], port=dataConfig["port"])