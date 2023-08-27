package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO AccountDAO;

    // no-args constructor for creating a new AccountService with a new AccountDAO.
    public AccountService(){
        AccountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param AccountDAO
     */
    public AccountService(AccountDAO AccountDAO){
        this.AccountDAO = AccountDAO;
    }

    /**
     * Uses the AccountDAO to retrieve all Accounts.
     *
     * @return all Accounts
     */
    public List<Account> getAllAccounts() {
        return AccountDAO.getAllAccounts();
    }
    
    /**
     * Uses the AccountDAO to persist an Account. The given Account will not have an id provided.
     *
     * @param Account an Account object.
     * @return The persisted Account if the persistence is successful.
     */
    public Account addAccount(Account Account) {
        return AccountDAO.insertAccount(Account);
    }
}
