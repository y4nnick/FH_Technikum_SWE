package at.technikum.wien.mse.swe.exception;

public class ConnectorReadException extends RuntimeException {
    public ConnectorReadException(String e) {
        super(e);
    }

    public ConnectorReadException(Exception e) {
        super(e);
    }
}
