# <u>Food Recipes </u>

---
#   <u>Description  </u>

  This is a backend application, used for the publishment of unique food Recipes. 
  Users have to use register the system after that they'll get an activation code which enables them to activate their accounts.
  After the activation process they'll get their token and be able to update their accounts, like the recipes published by the people who got the Role "Admin"
  and if there is a new recipe published which got the same category with the users' favorite, they'll receive mail about the new added recipe.
  Only people who got the role Admin will have the authorization about simple CRUD methods on recipes. User's can only add recipes to their favorite and use search and filter functions.

---
#   <u>Used Technologies and Programming Languages:</u>

###  Programming Language: Java, Spring Boot
###  Database: PostgreSQL, MongoDB
###  Http Request between Services: OpenFeign
###  Queue technology: RabbitMq
###  Caching: Redis
###  Logging: SLF4J (used on RabbitMq consumer and producer classes)
###  Mail: Google Mail Service
###  JSON convertor: Jackson2JsonMessageConverter
###  Other technologies: Mapstruct, JWT Web Tokens, Lombok, Swagger API, Zipkin, Spring Config

---
# <u>SERVICES</u>

---

* [AUTH SERVICE](http://localhost:9090/swagger-ui/index.html#/ "AUTH SERVICE")
* [USER PROFILE SERVICE](http://localhost:9080/swagger-ui/index.html#/ "USER PROFILE SERVICE")
* [RECIPE SERVICE](http://localhost:9070/swagger-ui/index.html#/ "RECIPE SERVICE")
  * RECIPE SERVICE
  * CATEGORY SERVICE
* [COMMENT SERVICE](http://localhost:9060/swagger-ui/index.html#/ "COMMENT SERVICE")
  * COMMENT SERVICE
  * POINT SERVICE
* CONFIG-SERVER SERVICE
* MAIL SERVICE


#   <u>API URLs</u>

#  <u> AUTH SERVICE</u>

  Auth Service, is the service where users will register and login to the system.
  After the registration process user's will get their token and use API Urls in order to take action on the website according to their roles.

---
> Swagger URL : http://localhost:9090/swagger-ui/index.html#/
---
### AUTH_Register
>  api/v1/auth/register

![Auth Register](Swagger-Screenshot/Auth/Auth_Register.png)

### AUTH_Activate-Status
>  api/v1/auth/activate-status


![Auth Activate-Status](Swagger-Screenshot/Auth/Auth_Activate-Status.png)

### AUTH_Login
>  api/v1/auth/login

![Auth Login](Swagger-Screenshot/Auth/Auth_Login.png)

### AUTH_Forgot-Password
>  api/v1/auth/forgot-password

![Auth Forgot-Password](Swagger-Screenshot/Auth/Auth_Forgot-Password.png)

### AUTH_FIND-ALL
>  api/v1/auth/find-all

![Auth Find-All](Swagger-Screenshot/Auth/Auth_Find-All.png)

---

# <u>USER SERVICE</u>

  User Service is where Users can update their user profile information,
  delete and inactivate their account and add the recipes they liked to their favorite list. 
  In order to take notifications about the newly added recipes which are in the same categories as user favorite recipes.

---
> Swagger URL : http://localhost:9080/swagger-ui/index.html#/

### USER_UPDATE
>  api/v1/user/update/{token}

![User Update](Swagger-Screenshot/User/User_Update.png)

### USER_INACTIVATE
>  api/v1/user/inactivate/{token}

![User Inactivate](Swagger-Screenshot/User/User_Inactivate.png)

### USER_CHANGE-PASSWORD
> api/v1/user/change-password

![User Change_Password](Swagger-Screenshot/User/User_Change-Password.png)

### USER_DELETE
> api/v1/user/delete/{token}

![User Delete](Swagger-Screenshot/User/User_Delete.png)

### USER_FIND-ALL
> api/v1/user/find-all

![User Find-All](Swagger-Screenshot/User/User_FindAll.png)

### USER_ADD-FAVORITE-RECIPE
> api/v1/user/add-favorite-recipe/{token}/{recipeId}

![User Add Favorite Recipe](Swagger-Screenshot/User/User_Add_Favorite_Recipe.png)

### USER_DROP-FAVORITE-RECIPE
> api/v1/user/drop-favorite-recipe/{token}/{recipeId}

![User Remove Favorite Recipe](Swagger-Screenshot/User/User_Drop_Favorite_Recipe.png)

---

# <u>RECIPE SERVICE</u>

  Recipe Service is the service where users can look for the recipes. 
  Search with pre-determined searches according to the ingredient names,
  categories and food names. Also, responsible who got the role "ADMIN" can create,
  update and delete new forms of categories and recipes.

---
> Swagger URL : http://localhost:9070/swagger-ui/index.html#/
---

#  <u>CATEGORY</u>


### CATEGORY_SAVE
> api/v1/category/save/{token}

![Category Save](Swagger-Screenshot/Recipe/Category/Category_Save.png)

### CATEGORY_UPDATE
> api/v1/category/update/{token}

![Category Update](Swagger-Screenshot/Recipe/Category/Category_Update.png)


### CATEGORY_FIND-ALL
> api/v1/category/find-all

![Category FindAll](Swagger-Screenshot/Recipe/Category/Category_FindAll.png)

---

##  <u>RECIPE</u>

### RECIPE_SAVE
> api/v1/recipe/save/{token}

![Recipe Save](Swagger-Screenshot/Recipe/Recipe/Recipe_Save.png)

### RECIPE_UPDATE
> api/v1/recipe/update/{token}

![Recipe Update](Swagger-Screenshot/Recipe/Recipe/Recipe_Update.png)

### RECIPE_SEARCH_WITH_INGREDIENT_NAME
> api/v1/recipe/search-filter-by-ingredient-names

![Recipe_Search_With_Ingredient_Name](Swagger-Screenshot/Recipe/Recipe/Recipe_Search_With_Food_Name.png)

### RECIPE_SEARCH_WITH_FOOD_NAME
> api/v1/recipe/search-filter-by-food-names

![Recipe_Search_With_Food_Name](Swagger-Screenshot/Recipe/Recipe/Recipe_Search_With_Food_Name.png)

### RECIPE_SEARCH_WITH_CATEGORYIDS
> api/v1/recipe/search-filter-by-categories

![Recipe_Search_With_CategoryIds](Swagger-Screenshot/Recipe/Recipe/Recipe_Search_With_CategoryIds.png)

### RECIPE_ORDER_ACCORDING_TO_CALORIES
> api/v1/recipe/order-recipes-by-calories

![Recipe_Order_By_Calories](Swagger-Screenshot/Recipe/Recipe/Recipe_Order_By_Calories.png)

### RECIPE_FIND_ALL_WITH_CACHE
> api/v1/recipe/findAllWithCache

![Recipe_Find-All_With_Cache](Swagger-Screenshot/Recipe/Recipe/Recipe_Find_All_With_Cache.png)

### RECIPE_FIND_ALL
> api/v1/recipe/find-all

![Recipe_Find-Al](Swagger-Screenshot/Recipe/Recipe/Recipe_Find-All.png)

### RECIPE_DELETE
> api/v1/recipe/delete-recipe/{token}/{recipeId}

![Recipe_Delete](Swagger-Screenshot/Recipe/Recipe/Recipe_Delete.png)

### CATEGORY_DELETE
> api/v1/category/delete/{token}

![Category Delete](Swagger-Screenshot/Recipe/Recipe/Category_Delete.png)

# <u>COMMENT SERVICE</u>

  Comment Service is the service where User actions will take place such as commenting a recipe and also score the
  recipes based on users' opinion.
  
##  <u>COMMENT</u>

### COMMENT_ADD
> api/v1/comment/add-comment/{token}

![Comment Add](Swagger-Screenshot/Comment/Comment/Comment_Add.png)

### COMMENT_UPDATE
> api/v1/comment/update-comment/{token}

![Comment Update](Swagger-Screenshot/Comment/Comment/Comment_Update.png)

### COMMENT_DELETE
> api/v1/comment/delete-comment/{token}

![Comment Delete](Swagger-Screenshot/Comment/Comment/Comment_Delete.png)

### COMMENT_FIND_ALL
> api/v1/comment/find-all

![Comment Find All](Swagger-Screenshot/Comment/Comment/Comment_Find_All.png)

##  <u>POINT</u>

### POINT_ADD
> api/v1/point/add-point/{token}

![Point Add](Swagger-Screenshot/Comment/Point/Point_Add.png)

### POINT_FIND_ALL
> api/v1/point/find-all/{token}

![Point Find All](Swagger-Screenshot/Comment/Point/Point_Find_All.png)

### POINT_REMOVE
> api/v1/point/remove-point/{token}

![Point Remove](Swagger-Screenshot/Comment/Point/Point_Remove.png)
