# Flask
from flask import Flask
from flask import jsonify
from flask import request
from flask_cors import CORS
from waitress import serve
# Utils
import json
import requests
import datetime
import re
# Token JWT
from flask_jwt_extended import create_access_token, verify_jwt_in_request
from flask_jwt_extended import get_jwt_identity
from flask_jwt_extended import jwt_required
from flask_jwt_extended import JWTManager

# ------------------------- Setting Flask App -------------------------------
app = Flask(__name__)
cors = CORS(app)


def loadFileConfig():
    with open("config.json") as f:
        data = json.load(f)
    return data


dataConfig = loadFileConfig()

headers = {"Content-Type": "application/json; charset=utf-8"}


# ------------------------- Middleware -------------------------------
def format_url():
    parts = request.path.split("/")
    url = request.path
    for part in parts:
        if re.search('\\d', part):
            url = url.replace(part, "?")
    return url


@app.before_request
def before_request_callback():
    excluded_routes = ["/login"]
    # Token
    if request.path not in excluded_routes:
        if not verify_jwt_in_request():
            return jsonify({"msg": "Permission Denied"}), 401
        # Roles and Permissions
        user = get_jwt_identity()
        print(user)
        if user["role"] is None:
            return jsonify({"msg": "Permission Denied"}), 401
        else:
            role_id = user["role"]["_id"]
            route = format_url()
            method = request.method
            has_permission = validate_permission(role_id, route, method)
            if not has_permission:
                return jsonify({"msg": "Permission Denied"}), 401


def validate_permission(role_id, route, method):
    url = dataConfig["url-security"] + "/role-permission/validate/role/" + role_id
    body = {"url": route, "method": method}
    print(url)
    print(body)
    response = requests.post(url, json=body, headers=headers)
    print(response)
    try:
        data = response.json()
        if "id" in data:
            return True
    except:
        return False


# ------------------------- Setting JWT Token -------------------------------
app.config["JWT_SECRET_KEY"] = dataConfig["jwt-key"]
jwt = JWTManager(app)


# ------------------------- Endpoints -------------------------------
@app.route("/test", methods=["GET"])
def test():
    return jsonify({"msg": "It works!"})


@app.route("/login", methods=["POST"])
def login():
    # FE -> AGW
    data = request.get_json()
    # AGW -> MS
    url = dataConfig["url-security"] + "/user/validate"
    response = requests.post(url, json=data, headers=headers)
    if response.status_code == 401:
        return jsonify({"msg": "Usuario o contraseña incorrectos"}), 401
    elif response.status_code == 500:
        return jsonify({"msg": "Error inesperado"}), 500
    elif response.status_code == 200:
        user = response.json()
        expires = datetime.timedelta(seconds=60 * 60 * 24)
        access_token = create_access_token(identity=user, expires_delta=expires)
        return jsonify({"token": access_token, "user_id": user["_id"]})


"""
--------------------------SPRING - USER------------------------------
"""


@app.route("/user", methods=["GET"])
def get_users():
    url_security = dataConfig["url-security"] + "/user"
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/user/<string:id>", methods=["GET"])
def get_user(id):
    url_security = dataConfig["url-security"] + "/user/" + id
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/user", methods=["POST"])
def create_user():
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/user"
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/user/<string:id>", methods=["PUT"])
def update_user(id):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/user/" + id
    response = requests.put(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/user/<string:id>", methods=["DELETE"])
def delete_user(id):
    url_security = dataConfig["url-security"] + "/user/" + id
    response = requests.delete(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/user/<string:id_user>/role/<string:id_role>", methods=["PUT"])
def add_role_to_user(id_user, id_role):
    url_security = dataConfig["url-security"] + "/user/" + id_user + "/role/" + id_role
    response = requests.put(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/user/validate", methods=["POST"])
def validate_user():
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/user/validate"
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


"""
--------------------------SPRING - ROLE------------------------------
"""


@app.route("/role", methods=["GET"])
def get_roles():
    url_security = dataConfig["url-security"] + "/role"
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/role/<string:id>", methods=["GET"])
def get_role(id):
    url_security = dataConfig["url-security"] + "/role/" + id
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/role", methods=["POST"])
def create_role():
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/role"
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/role/<string:id>", methods=["PUT"])
def update_role(id):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/role/" + id
    response = requests.put(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/role/<string:id>", methods=["DELETE"])
def delete_role(id):
    url_security = dataConfig["url-security"] + "/role/" + id
    response = requests.delete(url_security, headers=headers)
    return jsonify(response.json())


"""
--------------------------SPRING - PERMISSION------------------------------
"""


@app.route("/permission", methods=["GET"])
def get_permissions():
    url_security = dataConfig["url-security"] + "/permission"
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/permission/<string:id>", methods=["GET"])
def get_permission(id):
    url_security = dataConfig["url-security"] + "/permission/" + id
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/permission", methods=["POST"])
def create_permission():
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/permission"
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/permission/<string:id>", methods=["PUT"])
def update_permission(id):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/permission/" + id
    response = requests.put(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/permission/<string:id>", methods=["DELETE"])
def delete_permission(id):
    url_security = dataConfig["url-security"] + "/permission/" + id
    response = requests.delete(url_security, headers=headers)
    return jsonify(response.json())


"""
--------------------------SPRING - ROLEPERMISSION------------------------------
"""


@app.route("/role-permission", methods=["GET"])
def get_role_permissions():
    url_security = dataConfig["url-security"] + "/role-permission"
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/role-permission/<string:id>", methods=["GET"])
def get_role_permission(id):
    url_security = dataConfig["url-security"] + "/role-permission/" + id
    response = requests.get(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/role-permission/role/<string:id_role>/permission/<string:id_permission>", methods=["POST"])
def create_role_permission(id_role, id_permission):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/role-permission/role/" + id_role + "/permission/" + id_permission
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/role-permission/<string:id_role_permission>/role/<string:id_role>/permission/<string:id_permission>", methods=["PUT"])
def update_role_permission(id_role_permission, id_role, id_permission):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/role-permission/" + id_role_permission + "/role/" + id_role + "/permission/" + id_permission
    response = requests.put(url_security, json=body, headers=headers)
    return jsonify(response.json())


@app.route("/role-permission/<string:id>", methods=["DELETE"])
def delete_role_permission(id):
    url_security = dataConfig["url-security"] + "/role-permission/" + id
    response = requests.delete(url_security, headers=headers)
    return jsonify(response.json())


@app.route("/role-permission/validate/role/<string:id_role>", methods=["POST"])
def validate_role_permission(id_role):
    body = request.get_json()
    url_security = dataConfig["url-security"] + "/role-permission/validate/role/" + id_role
    response = requests.post(url_security, json=body, headers=headers)
    return jsonify(response.json())


# ------------------------- Server -------------------------------
url = "http://" + dataConfig["url-apigateway"] + ":" + dataConfig["port-apigateway"];
print("Server running: " + url)
serve(app, host=dataConfig["url-apigateway"], port=dataConfig["port-apigateway"])
