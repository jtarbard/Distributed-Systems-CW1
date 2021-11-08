from flask import Flask

app = Flask(__name__)

# Routes
@app.route("/")
def hello_world():
    return "Hello World"

@app.route("/review/<title>")
def get_review_by_title():
    # Returns first row matching title

    return 0

# Run
if __name__ == "__main__":
    app.run(host="localhost", port=7777)