from os import error
from types import MethodType
from flask import Flask, request
from flask_restful import reqparse, abort, Api, Resource
import data

app = Flask(__name__)
api = Api(app)

data.parse_data()

def abort_if_name_doesnt_exist(name):
    return

class metacritic(Resource):
    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return dict(zip(data.metacritic_cols, data.get_metacritic_by_name(name)))

class steam(Resource):
    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return dict(zip(data.steam_cols, data.get_steam_original_price_by_name(name)))

class client(Resource):
    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return dict(zip(data.client_cols, data.get_client_reviews_by_name(name)))

api.add_resource(metacritic, '/metacritic/<name>')
api.add_resource(steam, '/steam/<name>')


# Run
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=7777)