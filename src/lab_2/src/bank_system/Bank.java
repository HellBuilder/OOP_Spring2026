package bank_system;

import java.util.Vector;

public class Bank {

    private Vector<Account> accounts;

    public Bank() {
        accounts = new Vector<>();
    }

    public void openAccount(Account acc) {
        accounts.add(acc);
    }

    public void closeAccount(int accNumber) {

        accounts.removeIf(acc -> acc.getAccountNumber() == accNumber);
    }

    public Account findAccount(int accNumber) {

        for (Account acc : accounts) {

            if (acc.getAccountNumber() == accNumber)
                return acc;
        }

        return null;
    }

    public void update() {

        for (Account acc : accounts) {

            if (acc instanceof SavingsAccount) {

                ((SavingsAccount) acc).addInterest();
            }

            if (acc instanceof CheckingAccount) {

                ((CheckingAccount) acc).deductFee();
            }
        }
    }

    public void printAccounts() {

        for (Account acc : accounts)
            acc.print();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {

        Bank bank = new Bank();

        SavingsAccount s1 = new SavingsAccount(1001, 5);
        CheckingAccount c1 = new CheckingAccount(2001);
        Account a1 = new Account(3001);

        bank.openAccount(s1);
        bank.openAccount(c1);
        bank.openAccount(a1);

        s1.deposit(1000);
        c1.deposit(500);
        c1.withdraw(100);
        c1.withdraw(50);
        c1.deposit(200);

        bank.update();

        bank.printAccounts();
    }
}