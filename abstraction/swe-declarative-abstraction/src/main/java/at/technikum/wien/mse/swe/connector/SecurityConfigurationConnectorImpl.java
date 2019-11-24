package at.technikum.wien.mse.swe.connector;

import java.nio.file.Path;

import at.technikum.wien.mse.swe.SecurityConfigurationConnector;
import at.technikum.wien.mse.swe.model.*;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityConfigurationConnectorImpl implements SecurityConfigurationConnector {
    @Override
    public SecurityConfiguration read(Path file) {
        return Connector.read(file, SecurityConfiguration.class);
    }
}
