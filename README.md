# rewardPointAPI

## end points
| Request  | Response |
| ------------- | ------------- |
| GET /user/{id}/total-reward  | total reward point in the last three months  |
| GET /user/{id}/monthly-reward  @RequestParam String month| reward point of requested month |
| GET /transaction/{id} | detailed transaction information|
| POST /user/{id} @RequestBody ShoppingTransaction trans| add a new transaction |
