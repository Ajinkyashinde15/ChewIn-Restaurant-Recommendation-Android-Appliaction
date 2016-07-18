---
layout: post
title:  "The Recommender System Addition"
date:   2016-07-18 12:00:30
first-name: Soumyadipta Nayak
last-name:  Ademola Kazeem
---
Written by: {{ page.first-name }} 

Posted by: {{ page.last-name }} 

### The Recommender System Addition.
The development of our application had two important features that needed to be implemented (i) Recommendation of restaurants (ii) menu search of the restaurants. Apart from these two main features we had other features that was in our product backlog like (i) bookmark feature of restaurants (ii) rating feature 
Of the above features, we were able to successfully implement the recommendation features, the bookmark, check-in and Facebook login feature in the last sprint. Our last sprint also had a task of collecting the menus of restaurants manually by calling different restaurants or visiting their restaurants. This task has been carried over to the next sprint due to the large volume of the restaurants.


### Mock-ups / Wire Frames.
### Tasks Completed
Recommendation of restaurants has been done completely in JAVA working on the cuisines, average cost, price range (most pocket friendly  costliest) and aggregate rating from users features of a restaurant to recommend. We have followed the content based recommendation technique to recommend the restaurants. We will have all the past check in data of a user with all the restaurant he likes with us. This is the data we will use to train our recommender system. We will try and find out the similarity scores between the restaurants the user has rated highly and the restaurants in our database. We can find the similarity score by different methods like Jaccard/ Cosine/ overlapping etc. For now, we are using the overlap technique to find the similarity score. We have also assigned relative weight to each parameter so that we can get a better recommendation. 
We have also implemented the rating feature that allows the user to rate a restaurant and give it a rating according to his liking. Once he gives a rating it gets stored in the user history and is used for recommendation.
One more feature that we have implemented is the bookmark feature, this will help the user to save/bookmark restaurants for future reference.

### Tasks in progress

Currently we are working on the code to make the menu search functionality. Apart from that we are manually collecting data from different restaurants to get the menu data. Alongside this we are also trying to figure out an easier way to collect menu from restaurants. We are also trying to refine our recommender system, evaluate the whole system and integrate the android functionalities and the server side functionalities.

### Next on the development.

Most of the android work is done and we have a working recommender system. We now are focusing only on the menu based search feature, android-server integration and refining our recommender system. Our menu data collection task is almost over and we will focus more on suggesting restaurants based on cuisines, cost, price range and aggregate rating. 

### Challenges.

*	Collecting the menu from different restaurants by either calling them or visiting their website is slow and difficult task. We have been denied by 3 websites for access to their menu database because they do not entertain academic projects. So our major challenge is to get menu database ready for all the restaurants.
*	We are also searching based on menu but many API’s don’t have the menu option. So collecting menu from different API’s is a humongous task.
