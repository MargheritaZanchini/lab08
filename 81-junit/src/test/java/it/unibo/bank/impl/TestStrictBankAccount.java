package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.SimpleBankAccount.ATM_TRANSACTION_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

import javax.naming.InitialContext;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    //public static final double TRANSACTION_FEE = 0.1;
    //public static final double MANAGEMENT_FEE = 5;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, INITIAL_AMOUNT);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        Assertions.assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
        Assertions.assertEquals(0, bankAccount.getTransactionsCount());
        Assertions.assertEquals(mRossi, bankAccount.getAccountHolder());
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT);
        Assertions.assertTrue(bankAccount.getTransactionsCount() > 0);
        double expectedValue = bankAccount.getBalance() - (MANAGEMENT_FEE + TRANSACTION_FEE * bankAccount.getTransactionsCount());
        
        bankAccount.chargeManagementFees(mRossi.getUserID());
        Assertions.assertTrue(bankAccount.getTransactionsCount() == 0);
        
        Assertions.assertEquals(expectedValue, bankAccount.getBalance());
    }

    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -INITIAL_AMOUNT);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    // 5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), bankAccount.getBalance() + INITIAL_AMOUNT);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
