import csv

# CSV storage
metacritic_cols = []
metacritic_rows = []

steam_cols = []
steam_rows = []

client_cols = []
client_rows = []

def parse_data():
    global metacritic_cols, metacritic_rows
    global steam_cols, steam_rows

    # Parse metacritic CSV into storage
    with open("metacritic_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        metacritic_cols = next(reader)
        for row in reader:
            metacritic_rows.append(row)

    # Parse steam CSV into storage
    with open("steam_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        steam_cols = next(reader)
        for row in reader:
            steam_rows.append(row)

    with open("client_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=",")
        client_cols = next(reader)
        for row in reader:
            steam_rows.append(row)

parse_data()

# Metacritic methods
def get_metacritic_metascore_by_name(name):
    col_name = metacritic_cols.index("name")
    col_metascore = metacritic_cols.index("metascore")

    for row in metacritic_rows:
        if row[col_name] == name:
            return str(row[col_metascore])

    return None

def get_metacritic_userscore_by_name(name):
    col_name = metacritic_cols.index("name")
    col_userscore = metacritic_cols.index("userscore")

    for row in metacritic_rows:
        if row[col_name] == name:
            return str(row[col_userscore])

    return None

def get_metacritic_scores_by_name(name):

    return get_metacritic_metascore_by_name(name)+","+get_metacritic_userscore_by_name(name)

# Steam Methods
def get_steam_url_by_name(name):
    col_name = steam_cols.index("name")
    col_url = steam_cols.index("url")

    for row in steam_rows:
        if row[col_name] == name:
            return str(row[col_url])
    
    return None

def get_steam_all_reviews_by_name(name):
    col_name = steam_cols.index("name")
    col_reviews = steam_cols.index("all_reviews")

    for row in steam_rows:
        if row[col_name] == name:
            return str(row[col_reviews])
    
    return None

def get_steam_original_price_by_name(name):
    col_name = steam_cols.index("name")
    col_price = steam_cols.index("original_price")

    for row in steam_rows:
        if row[col_name] == name:
            return str(row[col_price])
    
    return None    

# Client Methods
#review_id,user_id,game,comment,score
def get_client_scores_by_name(name):


def get_client_comments_by_name(name):


def get_client_score_by_name_and_user_id(name, user_id):


def get_client_comment_by_name_and_user_id(name, user_id):


def post_client_review(name, user_id, comment, score):


def update_client_comment_by_name_and_user_id(name, user_id, comment):


def update_client_score_by_name_and_user_id(name, user_id, score):


def delete_client_review_by_name_and_user_id(name, user_id):


