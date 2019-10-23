package repository.hibernate;

import model.user.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.ILoginRepository;

import java.util.List;

public class LoginHibernateRepository implements ILoginRepository {

    @Override
    public boolean searchUser(UserDTO user) {
        List<UserDTO> userList = findAll();
        for (UserDTO u : userList){
            if (user.equals(u)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void save(UserDTO entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(UserDTO entity) {

    }

    @Override
    public UserDTO findOne(Integer integer) {
        return null;
    }

    @Override
    public List<UserDTO> findAll() {
        System.out.println("lalallllalllalalaa");
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                List<UserDTO> userList = session.createQuery("from UserDTO",UserDTO.class).list();
                System.out.println( userList.size() + " curse(s) gasite:" );
                tx.commit();
                return userList;
            }catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
                return null;
            }
        }
    }
}
