import csv
from datetime import datetime

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
    global client_cols, client_rows

    # Parse metacritic CSV into storage
    with open("Web API 3/resources/metacritic_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        metacritic_cols = next(reader)
        for row in reader:
            metacritic_rows.append(row)

    # Parse steam CSV into storage
    with open("Web API 3/resources/steam_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        steam_cols = next(reader)
        for row in reader:
            steam_rows.append(row)

    with open("Web API 3/resources/client_games.csv") as csv_file:
        reader = csv.reader(csv_file, delimiter=",")
        client_cols = next(reader)
        for row in reader:
            client_rows.append(row)

parse_data()

# Metacritic methods

def get_metacritic_by_name(name):
    metacritic_name_index = metacritic_cols.index("name")
    
    for row in metacritic_rows:
        if row[metacritic_name_index].lower() == name.lower():
            return row


# Steam Methods
def get_steam_original_price_by_name(name):
    steam_name_index = steam_cols.index("name")

    for row in steam_rows:
        if row[steam_name_index].lower() == name.lower():
            return row
    
    return None    

# Client colums, all columns included
client_review_id_index = client_cols.index("review_id")
# where 'review' encompases all columns below
client_user_id_index = client_cols.index("user_id")
client_name_index = client_cols.index("name")
client_comment_index = client_cols.index("comment")
cleint_score_index = client_cols.index("score")
client_date_index = client_cols.index("date")

# Client Methods
def get_client_reviews_by_user_id(user_id):
    tmp = []

    for row in client_rows:
        if row[client_user_id_index] == user_id:
            tmp.append(row)
    
    return tmp

def get_client_reviews_by_user_id_and_name(user_id, name):
    tmp = []

    for row in client_rows:
        if row[client_user_id_index] == user_id and row[client_name_index]== name:
            tmp.append(row)
    
    return tmp

def get_client_reviews_by_name(name):
    tmp = []

    for row in client_rows:
        if row[client_name_index]== name:
            tmp.append(row)
    
    return tmp

def get_client_review_score_by_name(name):
    score = None
    count = 0

    for row in client_rows:
        if row[client_name_index]== name:
            score=+row[score]
    
    if score == None:
        return None
    else:
        return score/count

def post_client_review(user_id, name, comment, score):
    #TODO: create validation
    try:
        client_rows.append(client_rows[-1]+1, user_id, name, comment, score, datetime.now())
        return True
    except:
        return False

def update_client_review(review_id, user_id, comment, score):
    try:
        for row in client_rows:
            if row[client_review_id_index] == review_id and row[client_user_id_index] == user_id:
                row[client_comment_index] = comment
                row[cleint_score_index] = score
                row[client_date_index] = datetime.now()
                return True
        return False
    except:
        return False

def delete_client_review(review_id, user_id):
    try:
        for row in client_rows:
            if row[client_review_id_index] == review_id and row[client_user_id_index] == user_id:
                row.remove()
                return True
        return False
    except:
        return False
