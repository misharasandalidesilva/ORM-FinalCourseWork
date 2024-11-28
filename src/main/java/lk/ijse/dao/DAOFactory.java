package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER, PROGRAM, STUDENT,REGISTRATION,PAYMENT,QUERY
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                    return new UserDAOImpl();
            case PROGRAM:
                    return new ProgramDAOImpl();
            case STUDENT:
                    return new StudentDAOImpl();
            case REGISTRATION:
                return new RegistretionDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case QUERY:
                return new QuearyDAOImpl();
                default:
                    return null;
            }
        }

}

