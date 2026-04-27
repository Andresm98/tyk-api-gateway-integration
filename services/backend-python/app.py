from flask import Flask, jsonify

app = Flask(__name__)


@app.route("/hello")
def hello():
    return jsonify(
        {
            "message": "Hello world from Python Flask on port 5000!",
            "runtime": "Python 3.11",
            "status": "active",
        }
    )


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
