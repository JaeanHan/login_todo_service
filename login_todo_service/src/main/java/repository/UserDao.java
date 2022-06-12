package repository;

import entity.User;
import exception.DuplicateUsernameException;

public interface UserDao {
	public int checkUsernameDuplicate(String username) throws DuplicateUsernameException;
	public int SignUp(User user);
	public User getUserByUsername(String username);
	public User SignIn(String username, String password);
	public int updateProfile(User user);
	public int deleteUserByUsername(User user);
	public int checkValidEmail(String email); // email confirmation
	public int resetPassword(String username, String email, String newPassword);
	
}
