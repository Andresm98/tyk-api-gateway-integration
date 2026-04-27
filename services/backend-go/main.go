package main

import (
	"encoding/json"
	"net/http"
)

func main() {
	http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		json.NewEncoder(w).Encode(map[string]string{"status": "alive", "service": "Go Backend on port 8081"})
	})
	http.ListenAndServe(":8081", nil)
}
