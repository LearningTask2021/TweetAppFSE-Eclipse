package com.tweetApp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoSocketOpenException;
import com.tweetApp.model.Tweets;
import com.tweetApp.model.Users;
import com.tweetApp.repository.UsersRepository;

@Service
public class TweetsService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	public Users registerNewUser(Users user) {
		Users newUser=usersRepository.save(user);
		System.out.println(newUser);
		return newUser;
	}
	
	public Users loginUser(String userId,String password) {
		Users user=usersRepository.findByUserIdAndPassword(userId,password).get();
		System.out.println(user);
		return user;
	}
	
	public Users resetPassword(String userId,String password) {
		Users user=usersRepository.findByUserId(userId).get();
		user.setPassword(password);
		usersRepository.save(user);
		return user;
	}
	
	public List<Tweets> retrunAllTweets(){
		List listOfTweets = new ArrayList<>();
        usersRepository.findAll().forEach(
        		user->{
        			listOfTweets.add(user.getTweets());
        		});
        return listOfTweets;
	}
	
	public List<Users> retrunAllUsers(){
		List listOfUsers = new ArrayList<>();
        usersRepository.findUsersAndExcludePassword().forEach(listOfUsers::add);
        return listOfUsers;
	}
	
	public List<Tweets> returnTweetsOfUSer(String userId){
		Users user = usersRepository.findByUserId(userId).get();
		List<Tweets> tweets=user.getTweets();
		return tweets;	
	}
	
	public List<Users> returnUsersContainingName(String userId){
		List<Users> users=this.retrunAllUsers();
		return users.stream()
                .filter(x ->x.getUserId().indexOf(userId)>=0)
                .collect(Collectors.toList());
	    
	}
	
	public String PostATweet(Tweets tweet,String userId) {
		Users user=usersRepository.findByUserId(userId).get();
    	List<Tweets> tweets=user.getTweets();
    	tweets.add(tweet);
    	Users updatedUser=usersRepository.save(user);
    	return "Posted the tweet successfully!";
	}
	
	public String deleteATweet(String userId,String tweetId) {
		Users user=usersRepository.findByUserId(userId).get();
		List<Tweets> tweets=user.getTweets();
		tweets.removeIf(t -> t.getTweetId() == tweetId);
		Users updatedUser=usersRepository.save(user);
		return "Tweet deleted succcessfully!";
	}
	
	public String replyATweet(String userId,String parentTweetId,Tweets reply) {
		System.out.println("Inside tweets method!");
		reply.setParentTweetId(parentTweetId);
     	Users user=usersRepository.findByUserId(userId).get();
     	List<Tweets> tweets=user.getTweets();
     	tweets.add(reply);
     	Users user1=usersRepository.findByTweetsTweetId(parentTweetId).get();
     	System.out.println(user1);
     	List<Tweets> tweets1=user1.getTweets();
     	tweets1.forEach(t->{
     	if(t.getTweetId().contentEquals(parentTweetId)) {
     		t.getReplies().add(reply);
     	}
     	});
     	System.out.println(tweets1);
     	user1.setTweets(tweets1);
     	usersRepository.save(user1);
     	Users updatedUser=usersRepository.save(user);
     	return "Posted the reply!";
	}
	
	public String updateATweet(String userId,String tweetId,Tweets tweet) {
		Users user=usersRepository.findByUserId(userId).get();
		List<Tweets> tweets=user.getTweets();
		tweets.removeIf(t -> t.getTweetId() == tweetId);
		tweets.add(tweet);
		Users updatedUser=usersRepository.save(user);
		return "Updated tweet succcesfully";
	}
	
	public List<Tweets> likeATweet(String userId,String tweetId) {
		Users user=usersRepository.findByUserId(userId).get();
		List<Tweets> tweets=user.getTweets();
		tweets.forEach(t->{
			if(t.getTweetId().contentEquals(tweetId)) {
				t.setLikes(t.getLikes()+1);
				System.out.println(t.getLikes());
			}
		});
		user.setTweets(tweets);
		usersRepository.save(user);
		return user.getTweets();
	}
}
