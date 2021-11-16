import csv
from datetime import datetime
import asyncio

# CSV storage
metacritic_cols = []
metacritic_rows = []

steam_cols = []
steam_rows = []

client_cols = []
client_rows = []

def parse_metacritic():
    global metacritic_cols, metacritic_rows

    with open("resources/metacritic_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        metacritic_cols = next(reader)
        for row in reader:
            metacritic_rows.append(row)

def parse_steam():
    global steam_cols, steam_rows

    # Parse steam CSV into storage
    with open("resources/steam_games.csv", encoding="utf8") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        steam_cols = next(reader)
        for row in reader:
            steam_rows.append(row)

def parse_client():
    global client_cols, client_rows

    with open("resources/client_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=",")
        client_cols = next(reader)
        for row in reader:
            client_rows.append(row)
    
    print(client_rows)

def parse_data():
    parse_metacritic()
    parse_steam()
    parse_client()

# Metacritic methods

def get_metacritic_by_name(name):
    parse_metacritic()
    metacritic_name_index = metacritic_cols.index("name")
    
    for row in metacritic_rows:
        if row[metacritic_name_index].lower() == name.lower():
            return row
    
    return None


# Steam Methods
def get_steam_original_price_by_name(name):
    parse_steam()
    steam_name_index = steam_cols.index("name")

    for row in steam_rows:
        if row[steam_name_index].lower() == name.lower():
            return row
    
    return None

# Client Methods
def get_client_reviews_by_user_id_and_name(user_id, name):
    parse_client()
    client_user_id_index = client_cols.index("user_id")
    client_name_index = client_cols.index("name")

    for row in client_rows:
        if row[client_user_id_index] == user_id and row[client_name_index].lower() == name.lower():
            return row
    
    return False

def get_client_reviews_by_name(name):
    parse_client()
    client_name_index = client_cols.index("name")
    tmp = []

    for row in client_rows:
        if row[client_name_index].lower() == name.lower():
            tmp.append(row)
    return zip(client_cols, tmp)

def post_client_review(user_id, name, comment, score):
    parse_client()
    try:
        client_rows.append(user_id, name, comment, score, datetime.now())
        new_row = [user_id, name, comment, score, datetime.now()]
        print(client_rows, new_row)
        with open("Web API 3/resources/client_games.csv") as csv_file:
            writer = csv.writer(csv_file)
            writer.writerow(new_row)

        return True
    except:
        return False

# def put_client_review(review_id, user_id, comment, score):
#     try:
#         for row in client_rows:
#             if row[client_review_id_index] == review_id and row[client_user_id_index] == user_id:
#                 row[client_comment_index] = comment
#                 row[cleint_score_index] = score
#                 row[client_date_index] = datetime.now()
#                 return True
#         return False
#     except:
#         return False

# def delete_client_review(review_id, user_id):
#     try:
#         for row in client_rows:
#             if row[client_review_id_index] == review_id and row[client_user_id_index] == user_id:
#                 row.remove()
#                 return True
#         return False
#     except:
#         return False
