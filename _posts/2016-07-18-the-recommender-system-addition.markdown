---
layout: post
title:  "The Recommender System Addition"
date:   2016-07-18 12:00:30
first-name: Soumyadipta Nayak
last-name:  Ademola Kazeem
---
Written by: {{ page.first-name }} 

Posted by: {{ page.last-name }} 

### The Overview.

The key features of our app are the recommender system and the menu based search. Therefore, our major focus was on recommender system and we have successfully implemented that feature. Users are recommended restaurants based on their history and on the restaurant they Check In. Hence, we have completed the Bookmark and Check In feature in parallel to the recommender system.
The development of our application had two important features that needed to be implemented (i) Recommendation of restaurants (ii) menu search of the restaurants. Apart from these two main features we had other features that was in our product backlog like (i) bookmark feature of restaurants (ii) rating feature 
Of the above features, we were able to successfully implement the recommendation features, the bookmark, check-in and Facebook login feature in the last sprint. Our last sprint also had a task of collecting the menus of restaurants manually by calling different restaurants or visiting their restaurants. This task has been carried over to the next sprint due to the large volume of the restaurants.

### The Recommender System.

Recommendation of restaurants has been done entirely in JAVA; working on the cuisines, average cost, price range (most pocket friendly  most costliest) and aggregate rating from users. We have followed the content-based recommendation technique to recommend the restaurants. We will have the Check In history of users with the list of restaurants he likes; this is the data we have used to train our recommender system. We found out the similarity scores between the restaurants the user has highly rated along with other restaurants in our database. Different methods like Jaccard, Cosine and overlapping can be used to calculate similarities; we have used the overlap technique to find the similarity score. We have also assigned relative weight to each parameter so as to get a better recommendation. 
We have also implemented the rating feature that allows the user to rate a restaurant and give it a rating according to his liking. Once the user rates the restaurant, that data is stored in the user history and will be use that data for recommendation.
One more feature that we have implemented is the bookmark feature, which will help users to save/bookmark restaurants for future reference.

#### Hurdles in Recommender System

*	The group did not have any user rating for training the recommender system. So we created a database of users and their rating
*	Additionally lot of values was missing in the price range, average cost and so on. Hence, we filled the missing values by averaging the values in those columns.

###  Task In-Progress

*	Currently we are working on the code to make the menu search functionality. We are collecting all the menus manually and at the same time working on the code to enable menu based search
*	We are also trying to refine our recommender system, evaluate the whole system and integrate the android functionalities and the server side functionalities.
*	We are also working on the Check In feature to post the same on Facebook. Which means if user Check’s In at any restaurant then the same would be posted on Facebook.



### Challenges.

*	We are trying to find out ways to evaluate our recommender system. Since we have used Zomato API we will check our results against them, still we are unsure how to check for accuracy of our recommender system.
*	Since we are manually entering the menu data, the model turns out to be static and we are not able to implement any changes that happen in the menu.  Therefore, this is again a major hurdle to update the menu real time.
