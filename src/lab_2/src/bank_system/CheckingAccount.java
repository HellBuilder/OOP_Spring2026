package bank_system;

public class CheckingAccount extends Account {

    private int transactionCount;
    private int freeTransactions;
    private double transactionFee;

    public CheckingAccount(int accNumber) {
        super(accNumber);
        transactionCount = 0;
        freeTransactions = 2;
        transactionFee = 0.02;
    }

    @Override
    public void deposit(double sum) {
        super.deposit(sum);
        transactionCount++;
    }

    @Override
    public void withdraw(double sum) {
        super.withdraw(sum);
        transactionCount++;
    }

    public void deductFee() {

        if (transactionCount > freeTransactions) {

            int extraTransactions = transactionCount - freeTransactions;

            double fee = extraTransactions * transactionFee;

            super.withdraw(fee);
        }

        transactionCount = 0;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" + super.toString() + ", Transactions=" + transactionCount + "}";
    }
}