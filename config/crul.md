
1. **Get all Meals:**
   curl http://localhost:8080/topjava/rest/meals/
2. **Get Meal by id:**
   curl http://localhost:8080/topjava/rest/meals/100002
3. **Get Meals between date and time:**
   curl -d "startDate=2015-05-30&startTime=09:00:00&endDate=2015-05-30&endTime=20:00:00" http://localhost:8080/topjava/rest/meals/filter
4. **Create new Meal:** 
   curl -X POST -H "Content-Type: application/json; charset=UTF-8" -d "{\"dateTime\":\"2015-06-01T18:00:00\", \"description\":\"Созданный\", \"calories\":300}" http://localhost:8080/topjava/rest/meals
5. **Update Meal:**
   curl -X PUT -H "Content-Type: application/json" -d "{\"id\":\"100002\", \"dateTime\":\"2015-06-01T19:00:00\", \"description\":\"Обновленный ужин\",\"calories\":300}" http://localhost:8080/topjava/rest/meals
6. **Delete Meal:** 
   curl -X DELETE http://localhost:8080/topjava/rest/meals/100003 