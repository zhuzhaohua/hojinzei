package com.kobuta.rakuchin.hojinzei.repository;

import com.kobuta.rakuchin.hojinzei.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByUserName(String userName);

}
