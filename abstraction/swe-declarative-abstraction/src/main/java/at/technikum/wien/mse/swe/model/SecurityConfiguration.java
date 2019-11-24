package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.connector.FieldAlignment;
import at.technikum.wien.mse.swe.connector.StructureField;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityConfiguration {

    @StructureField(name = "value", alignment = FieldAlignment.LEFT, begin = 41, length = 12)
    private ISIN isin;

    @StructureField(name = "code", alignment = FieldAlignment.LEFT, begin = 53, length = 2)
    private RiskCategory riskCategory;

    @StructureField(alignment = FieldAlignment.RIGHT, begin = 55, length = 30)
    private String name;

    @StructureField(name= "currency", alignment = FieldAlignment.LEFT, begin = 85, length = 3)
    @StructureField(name= "value", alignment = FieldAlignment.RIGHT, begin = 88, length = 10)
    private Amount yearHighest;

    @StructureField(name= "currency", alignment = FieldAlignment.LEFT, begin = 85, length = 3)
    @StructureField(name= "value", alignment = FieldAlignment.RIGHT, begin = 98, length = 10)
    private Amount yearLowest;

    public ISIN getIsin() {
        return isin;
    }

    public void setIsin(ISIN isin) {
        this.isin = isin;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount getYearHighest() {
        return yearHighest;
    }

    public void setYearHighest(Amount yearHighest) {
        this.yearHighest = yearHighest;
    }

    public Amount getYearLowest() {
        return yearLowest;
    }

    public void setYearLowest(Amount yearLowest) {
        this.yearLowest = yearLowest;
    }
}
