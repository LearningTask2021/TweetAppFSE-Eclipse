package com.tweetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetApp.model.Tweets;
import com.tweetApp.model.Users;

public interface UsersRepository extends MongoRepository<Users,String>{
	
	@Query(value="{}", fields="{ password : 0,tweets:0}")
	List<Users> findUsersAndExcludePassword();
	Optional<Users> findByUserId(String userId);
	//Optional<Tweets> findByTweetsTweetId(String userId,String tweeetId);
	Optional<Users> findByTweetsParentTweetId(String parentTweetId);
	Optional<Users> findByTweetsTweetId(String tweetId);
	Optional<Users> findByUserIdAndPassword(String userId,String password);
	
	
}
