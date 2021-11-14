from os import error
from types import MethodType
from flask import Flask, request
import data

app = Flask(__name__)
data.parse_data()

# Routes
@app.route("/")
def hello_world():
    return "Hello World"

#improved routes

@app.route("/metacritic/<name>/<column>", methods = ["GET"])
def metacritic(name, column):

    if column is "all":
        return data.get_metacritic_all_by_name(name)
    elif column in data.metacritic_cols:
        if column == "metascore":
            return data.get_metacritic_metascore_by_name(name)
        elif column == "console":
            return data.get_metacritic_console_by_name(name)
        elif column == "userscore":
            return data.get_metacritic_userscore_by_name(name)
        elif column == "date":
            return data.get_metacritic_date_by_name(name)
    else:
        return False

@app.route("/steam/<name>/<column>", methods = ["GET"])
def steam(name, column):

    if column in data.steam_cols:
        if column == "url":
            return data.get_steam_url_by_name(name)
        elif column == "all_reviews":
            return data.get_steam_all_reviews_by_name(name)
        elif column == "popular_tags":
            return data.get_steam_popular_tags_by_name(name)
        elif column == "original_price":
            return data.get_steam_original_price_by_name(name)

    else:
        return False

@app.route("/client/", methods = ["GET", "POST"])
def client():
    if request.method == "GET":
        return 0
    else:
        return 0

    
# Run
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=7777)