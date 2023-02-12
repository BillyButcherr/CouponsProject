import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import enums.ClientType;

import java.sql.SQLException;

public class LoginManager {
    // ----- Singleton implementation -----
    private static final LoginManager instance = new LoginManager();
    private LoginManager() {}
    public static LoginManager getInstance(){
        return instance;
    }
    // ----- End of Singleton implementation -----
    /**
     * Handles login for all three client types.
     * @param email a client email address
     * @param password a client password
     * @param clientType a client type : admin, company or customer.
     * @return <p>
     *     upon successful login returns a new initialized, logged client object based on a given login credentials.
     *     when fails returns null.
     * </p>
     *
     * @throws SQLException SQL error
     */
    public ClientFacade Login(String email, String password, ClientType clientType) throws SQLException {
        ClientFacade clientFacade = null;
        switch (clientType){
            case Administrator:{clientFacade = new AdminFacade();}break;
            case Company:{clientFacade = new CompanyFacade();}break;
            case Customer:{clientFacade = new CustomerFacade();}break;
        }
        if(clientFacade.login(email, password)){
            return clientFacade;
        } else { // to make sure clientFacade with empty or -1 id is never used in future versions of code
            clientFacade = null;
        }
        return null;
    }
}
