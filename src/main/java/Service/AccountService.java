package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    // no-args constructor for creating a new AccountService with a new AccountDAO.
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param AccountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Uses the AccountDAO to retrieve all Accounts.
     * @return all Accounts
     */
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    /**
     * Uses the AccountDAO to verify that the given username and password, in the form of 
     * an account object, exists in the database.
     *
     * @param Account object
     * @return Account that belongs to the username and password
     */
    public Account verifyAccount(Account account) {
        return accountDAO.verifyAccount(account);
    }
    
    /**
     * Uses the AccountDAO to persist an Account. The given Account will not have an id provided.
     *
     * @param Account an Account object.
     * @return The persisted Account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    /**
     * Uses the AccountDAO to see if a username already exists in the account table.
     *
     * @param A String representing the username to be checked
     * @return true if that username already exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return accountDAO.usernameExists(username);
    }

    /**
     * Uses the AccountDAO to see if a given id belongs to a user in the account table
     *
     * @param int representing the account id
     * @return account belonging to id
     */
    public Account getUserById(int id) {
        return accountDAO.getUserById(id);
    }

}
