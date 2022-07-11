from flask import Flask
from flask import jsonify
from flask import request
from flask_cors import CORS
import json
from waitress import serve

import pymongo
import certifi


app = Flask(__name__)
cors = CORS(app)

ca = certifi.where()
client = pymongo.MongoClient('', tlsCAFile = ca)
db = client.test
print(db)

results_bd = client[""]
print(results_bd.list_collection_names())


def load_file_config():
    with open("config.json") as archivo:
        data = json.load(archivo)
    return data


@app.route("/")
def test():
    response = {
        "message": "It works!!!!",
        "errors": []
    }
    return jsonify(response)

"""
---------------------------
    ENDPOINTS STUDENT
--------------------------
"""






"""
---------------------------
    ENDPOINTS SUBJECT
--------------------------
"""




"""
---------------------------
    ENDPOINTS DEPARTMENT
--------------------------
"""



"""
---------------------------
    ENDPOINTS REGISTRATION
--------------------------
"""




if __name__ == '__main__':
    dataConfig = load_file_config()
    url = "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"])
    print("Server running on ", url)
    serve(app, host=dataConfig["url-backend"], port=dataConfig["port"])

