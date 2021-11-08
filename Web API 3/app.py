from flask import Flask
import data

app = Flask(__name__)
data.parse_data()

# Routes
@app.route("/")
def hello_world():
    return "Hello World"

@app.route("/review/<source>/<name>")
def get_review_by_name(source, name):
    print(source, name)
    if source == "all":
        data.get_all_reviews_by_name(name)
    elif source == "metacritic_metascore":
        return data.get_metacritic_metascore_by_name(name)
    elif source == "metacritic_userscore":
        return data.get_metacritic_userscore_by_name(name)
    elif source == "all_metacritic":
        return data.get_metacritic_scores_by_name(name)
    elif source == "steam_all_reviews":
        return data.get_steam_all_reviews_by_name(name)

@app.route("/steam/<info>/<name>")
def get_steam_info_by_name(info, name):
    print(info, name)
    if info == "url":
        return data.get_steam_url_by_name(name)
    elif info == "original_price":
        return data.get_steam_original_price_by_name(name)

# Run
if __name__ == "__main__":
    app.run(host="localhost", port=7777)