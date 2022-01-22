help:
	@egrep -h '\s##\s' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

.PHONY: help

# Tests #######################################################################

delete.api.serums.0: ## Delete serum with ID 0 (Serum Worms Sackrider)
	curl -i -X DELETE -H "Content-Type: application/json" http://localhost:8081/api/serums/0
	@echo

get.api.serums: ## Read all serums
	curl -i -X GET http://localhost:8081/api/serums
	@echo

get.api.serums.0: ## Read serum with ID 0
	curl -i -X GET http://localhost:8081/api/serums/0
	@echo

get.api.serums.100: ## Read non-existent serum
	curl -i -X GET http://localhost:8081/api/serums/100
	@echo

post.api.serums: ## Create new serum (Serum Ovaltine Splern)
	curl -i -X POST -H "Content-Type: application/json" -d @curl_post_api_serums.json http://localhost:8081/api/serums
	@echo

put.api.serums.1: ## Update serum with ID 1 (Serum Squids Overturf)
	curl -i -X PUT -H "Content-Type: application/json" -d @curl_put_api_serums.json http://localhost:8081/api/serums/1
	@echo
