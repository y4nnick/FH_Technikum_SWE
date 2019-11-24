package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.connector.FieldAlignment;
import at.technikum.wien.mse.swe.connector.StructureField;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityAccountOverview {

    @StructureField(padding = '0', alignment = FieldAlignment.RIGHT, begin = 41, length = 10)
    private String accountNumber;

    @StructureField(name = "code", alignment = FieldAlignment.LEFT, begin = 51, length = 2)
    private RiskCategory riskCategory;

    @StructureField(name= "lastname", alignment = FieldAlignment.RIGHT, begin = 53, length = 30)
    @StructureField(name= "firstname", alignment = FieldAlignment.RIGHT, begin = 83, length = 30)
    private DepotOwner depotOwner;

    @StructureField(name= "currency", alignment = FieldAlignment.LEFT, begin = 113, length = 3)
    @StructureField(name= "value", alignment = FieldAlignment.RIGHT, begin = 116, length = 17)
    private Amount balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public DepotOwner getDepotOwner() {
        return depotOwner;
    }

    public void setDepotOwner(DepotOwner depotOwner) {
        this.depotOwner = depotOwner;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "SecurityAccountOverview{" +
                "accountNumber='" + accountNumber + '\'' +
                ", riskCategory=" + riskCategory +
                ", depotOwner=" + depotOwner +
                ", balance=" + balance +
                '}';
    }
}
