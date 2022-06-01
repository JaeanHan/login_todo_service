package repository;

import entity.User;

public interface UserDao {
	public int SignUp(User user);
	public User getUserByUsername(String username);
	public User SignIn(String username, String password);
//	public void SignOut();
	public int updateProfile(User user);
	public int deleteUserByUsername(User user);
	
}
