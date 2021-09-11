# REST API using Hibernate/Spring-Boot

#### The task is:

Build a voting system for deciding where to have lunch.

-  2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

### Swagger is included in project
Swagger URL http://localhost:8080/swagger-ui/

## Curl Examples
### Admin endpoints
#### Create restaurant
curl -X POST "http://localhost:8080/admin/restaurants" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"address\\": \\"someAddress\\", \\"name\\": \\"Olympics Caffee\\"}"

#### Update restaurant
curl -X PUT "http://localhost:8080/admin/restaurants/2" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"address\\": \\"Charlotte Road\\", \\"name\\": \\"SHOREDITCH\\"}"
#### Delete restaurant
curl -X DELETE "http://localhost:8080/admin/restaurants/2" --user admin@gmail.com:password

#### Create dish
curl -X POST "http://localhost:8080/admin/dishes" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"dateAdded\\": \\"2021-09-04\\", \\"name\\": \\"12 Piece Chicken\\", \\"price\\": \\"12\\", \\"restaurantId\\": 1}"
#### Update dish
curl -X PUT "http://localhost:8080/admin/dishes/3" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"dateAdded\\": \\"2021-09-04\\", \\"name\\": \\"2 pc. Breast and Wing Combo\\", \\"price\\": 23, \\"restaurantId\\": 1}"
#### Delete dish
curl -X DELETE "http://localhost:8080/admin/dishes/6" --user admin@gmail.com:password
#### Get all Votes today
curl -X GET "http://localhost:8080/admin/votes" --user admin@gmail.com:password
#### Get any vote by Id
curl -X GET "http://localhost:8080/admin/votes/2" --user admin@gmail.com:password"
#### Get vote history
curl -X GET "http://localhost:8080/admin/votes/history" --user admin@gmail.com:password
#### Get vote history of one User
curl -X GET "http://localhost:8080/admin/votes/history/user/3" --user admin@gmail.com:password
#### Update any vote
curl -X PUT "http://localhost:8080/admin/votes/2" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 3}"
#### Delete any vote
curl -X DELETE "http://localhost:8080/admin/votes/2" --user admin@gmail.com:password

#### Get all dishes today
curl -X GET "http://localhost:8080/dishes" --user user@gmail.com:password
#### Get one dish
curl -X GET "http://localhost:8080/dishes/2" --user user@gmail.com:password
#### Get dish history
curl -X GET "http://localhost:8080/dishes/history" --user user@gmail.com:password"
#### Get dish history by restaurant
curl -X GET "http://localhost:8080/dishes/history/1" --user user@gmail.com:password

#### Get all restaurants
curl -X GET "http://localhost:8080/restaurants" --user user@gmail.com:password
#### Get one restaurant
curl -X GET "http://localhost:8080/restaurants/3" --user user@gmail.com:password"

#### Get vote of currently logged user
curl -X GET "http://localhost:8080/votes" --user user@gmail.com:password
#### Create Vote ( or Update if user has already voted today )
curl -X POST "http://localhost:8080/votes" --user user@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 1}"
#### Update vote of currently logged user
curl -X PUT "http://localhost:8080/votes" --user user@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 3}"
#### Get vote history of currently logged user
curl -X GET "http://localhost:8080/votes/history" --user user@gmail.com:password
#### Delete vote of currently logged user
curl -X DELETE "http://localhost:8080/votes" --user user@gmail.com:password
#### Get vote from logged user history
curl -X GET "http://localhost:8080/votes/history/4" --user user@gmail.com:password
