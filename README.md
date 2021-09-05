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

## Curl Examples ( Swagger is included in project )
### Admin endpoints
#### Create restaurant
curl -X POST "http://localhost:8080/admin/restaurant" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"address\\": \\"someAddress\\", \\"name\\": \\"Olympics Caffee\\"}"

#### Update restaurant
curl -X PUT "http://localhost:8080/admin/restaurant/2" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"address\\": \\"Charlotte Road\\", \\"name\\": \\"SHOREDITCH\\"}"
#### Delete restaurant
curl -X DELETE "http://localhost:8080/admin/restaurant/2" --user admin@gmail.com:password

#### Create dish
curl -X POST "http://localhost:8080/admin/dish" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"dateAdded\\": \\"2021-09-04\\", \\"name\\": \\"12 Piece Chicken\\", \\"price\\": \\"12\\", \\"restaurantId\\": 1}"
#### Update dish
curl -X PUT "http://localhost:8080/admin/dish/3" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"dateAdded\\": \\"2021-09-04\\", \\"name\\": \\"2 pc. Breast and Wing Combo\\", \\"price\\": 23, \\"restaurantId\\": 1}"
#### Delete dish
curl -X DELETE "http://localhost:8080/admin/dish/6" --user admin@gmail.com:password
#### Get all Votes today
curl -X GET "http://localhost:8080/admin/vote" --user admin@gmail.com:password
#### Get any vote by Id
curl -X GET "http://localhost:8080/admin/vote/2" --user admin@gmail.com:password"
#### Get vote history
curl -X GET "http://localhost:8080/admin/vote/history" --user admin@gmail.com:password
#### Get vote history of one User
curl -X GET "http://localhost:8080/admin/vote/history/user/3" --user admin@gmail.com:password
#### Update any vote
curl -X PUT "http://localhost:8080/admin/vote/2" --user admin@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 3}"
#### Delete any vote
curl -X DELETE "http://localhost:8080/admin/vote/2" --user admin@gmail.com:password

### Shared endpoints
#### Register new user
curl -X POST "http://localhost:8080/user/registration" --user user@gmail.com:password -H "Content-Type: application/json" -d "{ \\"email\\": \\"randomemail@gmail.com\\", \\"password\\": \\"somestrongpassword\\", \\"username\\": \\"randomusername\\"}"

#### Get all dishes today
curl -X GET "http://localhost:8080/dish" --user user@gmail.com:password
#### Get one dish
curl -X GET "http://localhost:8080/dish/2" --user user@gmail.com:password
#### Get dish history
curl -X GET "http://localhost:8080/dish/history" --user user@gmail.com:password"
#### Get dish history by restaurant
curl -X GET "http://localhost:8080/dish/history/1" --user user@gmail.com:password

#### Get all restaurants
curl -X GET "http://localhost:8080/restaurant" --user user@gmail.com:password
#### Get one restaurant
curl -X GET "http://localhost:8080/restaurant/3" --user user@gmail.com:password"

#### Get vote of currently logged user
curl -X GET "http://localhost:8080/vote" --user user@gmail.com:password
#### Create Vote ( or Update if user has already voted today )
curl -X POST "http://localhost:8080/vote" --user user@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 1}"
#### Update vote of currently logged user
curl -X PUT "http://localhost:8080/vote" --user user@gmail.com:password -H "Content-Type: application/json" -d "{ \\"restaurantId\\": 3}"
#### Get vote history of currently logged user
curl -X GET "http://localhost:8080/vote/history" --user user@gmail.com:password
#### Delete vote of currently logged user
curl -X DELETE "http://localhost:8080/vote" --user user@gmail.com:password
#### Get vote from logged user history
curl -X GET "http://localhost:8080/vote/history/1" --user user@gmail.com:password
