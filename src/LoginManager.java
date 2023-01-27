import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import enums.ClientType;

import java.sql.SQLException;

public class LoginManager {
    private static final LoginManager instance = new LoginManager();
    private LoginManager() {}
    public static LoginManager getInstance(){
        return instance;
    }
    public ClientFacade Login(String email, String password, ClientType clientType) throws SQLException {
        ClientFacade clientFacade = null;
        switch (clientType){
            case Administrator:{clientFacade = new AdminFacade();}break;
            case Company:{clientFacade = new CompanyFacade();}break;
            case Customer:{clientFacade = new CustomerFacade();}break;
        }
        if(clientFacade.login(email, password)){
            return clientFacade;
        }
        return null;
    }
}