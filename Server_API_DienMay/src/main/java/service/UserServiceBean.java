package service;

import dao.UserDAO;
import entity.User;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class UserServiceBean {

    private final UserDAO dao = new UserDAO();

    // Lấy tất cả User
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    // Lấy User theo ID
    public User getUserById(int id) {
        return dao.getUserById(id);
    }

    // Thêm User
    public void addUser(User user) {
        dao.addUser(user);
    }

    // Cập nhật User
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    // Xóa User
    public void deleteUser(int id) {
        dao.deleteUser(id);
    }

    // Tìm kiếm User
    public List<User> searchUser(String keyword) {
        return dao.searchUser(keyword);
    }

    // Đăng nhập
    public User login(String username, String password) {
        return dao.login(username, password);
    }

}