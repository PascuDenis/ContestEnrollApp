package repository;

import model.user.UserDTO;

public interface ILoginRepository extends ICrudRepository<Integer, UserDTO> {
    boolean searchUser(UserDTO user);
}
