package com.crud.api.services;

import com.crud.api.models.CustomUserDetails;
import com.crud.api.models.User;
import com.crud.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findById(username);
        if(optional.isPresent())
            return new CustomUserDetails(optional.get());
        else
            throw new UsernameNotFoundException("User not found");
    }

    //fetching user from database.
    public User getUser(String username) throws UsernameNotFoundException{
        Optional<User> optional = userRepository.findById(username);
        if(optional.isPresent())
            return optional.get();
        else
            throw new UsernameNotFoundException("User not found");
    }

    //updating user details
    public User updateUser(User user){
        try {
            User existingUser = getUser(user.getUsername());
            if(user.getName()!=null && !user.getName().equals(""))
                existingUser.setName(user.getName());
            if(user.getContactNo()!=null && !user.getContactNo().equals(""))
                existingUser.setContactNo(user.getContactNo());

            return userRepository.save(existingUser);
        }catch (Exception e){
            System.out.println("User not found.");
        }

        return null;
    }

    //deleting user
    public User deleteUser(String username){
        try {
            User user = getUser(username);
            userRepository.delete(user);
            return user;
        }catch (Exception e){
            System.out.println("user not found");
        }

        return null;
    }
}
