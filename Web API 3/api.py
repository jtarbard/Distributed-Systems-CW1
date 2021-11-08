import csv

metacritic_cols = []
metacritic_rows = []

steam_cols = []
steam_rows = []

# Parse metacritic CSV
with open("Web API 3/metacritic_games.csv") as csv_file:
    reader = csv.reader(csv_file, delimiter=',')
    metacritic_cols = next(reader)
    for row in reader:
        metacritic_rows.append(row)

# Parse steam CSV
with open("Web API 3/steam_games.csv") as csv_file:
    reader = csv.reader(csv_file, delimiter=',')
    steam_cols = next(reader)
    for row in reader:
        steam_rows.append(row)

print(metacritic_cols)
print(steam_cols)