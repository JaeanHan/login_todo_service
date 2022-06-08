package repository;

import entity.User;

public interface UserDao {
	public int SignUp(User user);
	public User getUserByUsername(String username);
	public User SignIn(String username, String password);
	public int updateProfile(User user);
	public int deleteUserByUsername(User user);
	public int checkValidEmail(String email); // email confirmation
//	public int checkValidString(String string);
	public int resetPassword(String username, String email, String newPassword);
	
}
