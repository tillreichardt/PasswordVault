package pwv.service;

import pwv.repository.impl.UserRepositoryImpl;
import pwv.domain.User;
import pwv.util.PasswordEncoder;
import pwv.util.EmailValidator;

import java.util.List;


public class UserService {
    private final UserRepositoryImpl userRepo;
    private final PasswordEncoder passwordEncoder = new PasswordEncoder();
    private final EmailValidator validator = new EmailValidator();

    public UserService(UserRepositoryImpl userRepo) {
        this.userRepo = userRepo;
    }

    public User registerNewUser(String name, String email, String rawPassword) {
        if(!validator.isValid(email)){
            throw new IllegalArgumentException("EMail '" + email + "' is not valid");
        }
        if (checkEmailExists(email)) {
            throw new IllegalArgumentException("EMail '" + email + "' is already registered");
        }
        
        String hashedPassword = passwordEncoder.hashPassword(rawPassword);
        User user = new User(name, email, hashedPassword);
        userRepo.save(user);
        return user;
    }

    public boolean checkEmailExists(String email) {
        List<User> users = userRepo.getAll();
        for(User user: users) {
            if (user.getEmail().equals(email)) {
                return true; // Email exists
            }
        }
        return false; // Email does not exist
    }

    public void deleteUser(String email) {
        List<User> users = userRepo.getAll();
        for(User user: users) {
            if (user.getEmail().equals(email)) {
                userRepo.delete(user.getId());
                return; // User deleted
            }
        }
        throw new IllegalArgumentException("User '" + email + "' not found");
    }

    public User updateEmail(String oldEmail, String newEmail){
        if(!checkEmailExists(oldEmail)) throw new IllegalArgumentException("EMail '" + oldEmail + "' was not found");
        if(!checkEmailExists(newEmail)){
            User oldUser = userRepo.getUserByEmail(oldEmail);
            User newUser = new User(oldUser.getName(), newEmail, oldUser.getPassword());
            userRepo.update(newUser, oldUser.getId());
            newUser.setId(oldUser.getId());
        return newUser;
        } else {
            throw new IllegalArgumentException("EMail '" + newEmail + "' is already registered");
        }
        
    }

    public User updatePassword(String email, String newRawPassword){
        if(!checkEmailExists(email)) throw new IllegalArgumentException("EMail '" + email + "' was not found");
        User oldUser = userRepo.getUserByEmail(email);
        String hashedPassword = passwordEncoder.hashPassword(newRawPassword);
        User newUser = new User(oldUser.getName(), email, hashedPassword);
        userRepo.update(newUser, oldUser.getId());
        newUser.setId(oldUser.getId());
        return newUser;
    }
    
    public boolean checkPassword(String email, String password){
        if(!checkEmailExists(email)) throw new IllegalArgumentException("EMail '" + email + "' was not found");
        User user = userRepo.getUserByEmail(email);
        return user.getPassword().equals(passwordEncoder.hashPassword(password));
    }
}
