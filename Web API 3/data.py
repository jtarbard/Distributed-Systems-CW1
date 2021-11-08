import csv

# CSV storage
metacritic_cols = []
metacritic_rows = []

steam_cols = []
steam_rows = []

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

    # print(metacritic_cols)
    print(steam_cols)

parse_data()

# Metacritic methods
def get_metascore_by_name(name):
    col_name = metacritic_cols.index("name")
    col_metascore = metacritic_cols.index("metascore")

    for row in metacritic_rows:
        if row[col_name] == name:
            return row[col_metascore]

    return -1

def get_userscore_by_name(name):
    col_name = metacritic_cols.index("name")
    col_userscore = metacritic_cols.index("userscore")

    for row in metacritic_rows:
        if row[col_name] == name:
            return row[col_userscore]

    return -1

def get_meta_and_user_scores_by_name(name):

    return get_metascore_by_name(name), get_userscore_by_name(name)

# Steam Methods

def get_steam_url_by_name(name):
    col_name = steam_cols.index("name")
    col_url = steam_cols.index("url")

    for row in steam_rows:
        if row[col_name] == name:
            return row[col_url]
    
    return -1

def get_steam_all_reviews_by_name(name):
    col_name = steam_cols.index("name")
    col_reviews = steam_cols.index("all_reviews")

    for row in steam_rows:
        if row[col_name] == name:
            return row[col_reviews]
    
    return -1

def get_steam_original_price_by_name(name):
    col_name = steam_cols.index("name")
    col_price = steam_cols.index("original_price")

    for row in steam_rows:
        if row[col_name] == name:
            return row[col_price]
    
    return -1    


def get_steam_mature_content_rating_by_name(name):
    col_name = steam_cols.index("name")
    col_mature = steam_cols.index("mature_content")

    for row in metacritic_rows:
        if row[col_name] == name:
            return row[col_mature]
    
    return -1    

print(get_steam_mature_content_rating_by_name("Portal 2"))
print(get_steam_all_reviews_by_name("Portal 2"))
print(get_steam_url_by_name("Portal 2"))
print(get_steam_original_price_by_name("Portal 2"))
