package com.tweetApp.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tweetApp.model.Tweets;
import com.tweetApp.model.Users;
import com.tweetApp.repository.UsersRepository;
import com.tweetApp.services.TweetsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetsController
{
    @Autowired
    TweetsService tweetsService;
    
    //Register a user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user){
    	try {
    		
		Users newUser=tweetsService.registerNewUser(user);
		
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
 
    	
    }
  //login a registered user  
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String userId,@RequestParam String password){
    	try {
    		
    		Users newUser=tweetsService.loginUser(userId,password);
    		System.out.println(newUser);
    		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        	}
        	catch(Exception e) {
        		
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        	}
    }
    
    //forgot password
    @GetMapping("/{userId}/forgot")
    public ResponseEntity<?> resetPassword(@PathVariable("userId") String userId,@RequestBody String password){
try {
    		
    		Users newUser=tweetsService.resetPassword(userId,password);
    		System.out.println(newUser);
    		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        	}
        	catch(Exception e) {
        		
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        	}
    }
    
    

    //Gets all the tweets of all the users
    @GetMapping("/all")
    public ResponseEntity<List> getAllTweeetsOfUser()
    {
        try
        {
            List<Tweets> listOfTweets=tweetsService.retrunAllTweets();
            if(listOfTweets.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfTweets, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Returns all the users 
    @GetMapping("/users/all")
    public ResponseEntity<List> getAllUsers()
    {
        try
        {
        	List<Users> listOfUsers=tweetsService.retrunAllUsers();
            if(listOfUsers.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //returns tweets of particular user
    @GetMapping("/{userId}")
    public ResponseEntity getTweetByuserId(@PathVariable("userId") String userId)
    {
        try
        {
            
        	List<Tweets> tweets=tweetsService.returnTweetsOfUSer(userId);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //searchByUserName usrname canbe complete or partial 
    @GetMapping("/user/search/{userId}")
    public ResponseEntity getUserByName(@PathVariable("userId")String userId) {
    	 try
         {
             
         	List<Users> users=tweetsService.returnUsersContainingName(userId);
             return new ResponseEntity<>(users, HttpStatus.OK);
         }
         catch (Exception e)
         {
        	 System.out.println(e.getMessage());
             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
//post a tweet
    @PostMapping("/{userId}/add")
    public ResponseEntity postTweet(@RequestBody Tweets tweet,@PathVariable("userId") String userId)
    {
        try
        {
        	String msg=tweetsService.PostATweet(tweet, userId);
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
        	System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //delete a tweet
    
    @DeleteMapping("/{userId}/delete/{tweetId}")
    public ResponseEntity deleteTweet(@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
	try
	{
		String msg=tweetsService.deleteATweet(userId, tweetId);
	    return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	catch (Exception e)
	{
		System.out.println(e);
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
    
    //reply to a tweet
    @PostMapping("/{userId}/reply/{parentTweetId}")
    public ResponseEntity replyATweet(@RequestBody Tweets reply,@PathVariable("userId") String userId,@PathVariable("parentTweetId") String parentTweetId) {
    	 try
         {
    		String msg=tweetsService.replyATweet(userId, parentTweetId, reply);
             return new ResponseEntity<>(msg, HttpStatus.CREATED);
         }
         catch (Exception e)
         {
         	System.out.println(e);
             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
    //update a tweet
    @PutMapping("/{userId}/update/{tweetId}")
    public ResponseEntity updateTweet(@RequestBody Tweets tweet,@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
    	try
    	{
    		String msg=tweetsService.updateATweet(userId, tweetId,tweet);
    	    return new ResponseEntity<>(msg, HttpStatus.CREATED);
    	}
    	catch (Exception e)
    	{
    		System.out.println(e);
    	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    //like a tweet
    @PutMapping("/{userId}/like/{tweetId}")
    public ResponseEntity likeATweet(@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
    	try
    	{
    		List<Tweets> tweets=tweetsService.likeATweet(userId,tweetId);
    		
    	    return new ResponseEntity<>(tweets, HttpStatus.CREATED);
    	}
    	catch (Exception e)
    	{
    		System.out.println(e);
    	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}
