package lk.ijse.bo;


import lk.ijse.bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){
    }
    public static BoFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BoFactory() : boFactory;
    }

    public enum BOTypes{
        USER,PROGRAM,STUDENT,REGISTRATION,PAYMENT
    }


    public SuperBo getBO(BOTypes types){
        switch (types){
            case PROGRAM:
                return new ProgramBOImpl();
            case USER:
                return new UserBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            default:
                return null;

        }

    }
}
