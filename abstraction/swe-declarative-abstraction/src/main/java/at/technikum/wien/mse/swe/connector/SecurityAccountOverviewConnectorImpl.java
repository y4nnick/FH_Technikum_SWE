package at.technikum.wien.mse.swe.connector;

import java.nio.file.Path;

import at.technikum.wien.mse.swe.SecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityAccountOverviewConnectorImpl implements
        SecurityAccountOverviewConnector {

    @Override
    public SecurityAccountOverview read(Path file) {
        return Connector.read(file, SecurityAccountOverview.class);
    }
}
