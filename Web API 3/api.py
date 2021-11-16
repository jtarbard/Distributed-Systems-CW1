from os import error
from types import MethodType
from flask import Flask, request
from flask_restful import reqparse, abort, Api, Resource
import data
import asyncio

app = Flask(__name__)
api = Api(app)

data.parse_data()

def abort_if_name_doesnt_exist(name):
    return

class metacritic(Resource):
    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return dict(zip( data.metacritic_cols,  data.get_metacritic_by_name(name)))

class steam(Resource):
    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return dict(zip( data.steam_cols,  data.get_steam_original_price_by_name(name)))

client_parser = reqparse.RequestParser()
client_parser.add_argument("user_id")
client_parser.add_argument("name")
client_parser.add_argument("comment")
client_parser.add_argument("score")

class client(Resource):

    def get(self, name):
        name = name.replace("_", " ")
        abort_if_name_doesnt_exist(name)
        return data.get_client_reviews_by_name(name)

    def post(self, name):
        args = client_parser.parse_args()
        data.post_client_review(args["user_id"], name, args["comment"], args["score"])
        return data.client_rows[-1]

api.add_resource(metacritic, '/metacritic/<name>', methods=["GET"], endpoint="metacritic")
api.add_resource(steam, '/steam/<name>', methods=["GET"], endpoint="steam")
api.add_resource(client, "/client/<name>", methods=["GET", "POST"], endpoint="client")

# Run
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=7777)