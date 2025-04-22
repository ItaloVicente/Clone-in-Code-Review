import java.util.ArrayList;
import java.util.List;

public class ConfigService extends AbstractService {

    private static final SelectionStrategy STRATEGY = new RandomSelectionStrategy();
    private static final EndpointFactory FACTORY = new ConfigEndpointFactory();
    private static final int INITIAL_ENDPOINTS = 1;

    private final String hostname;
    private final String bucket;
    private final String password;
    private final int port;
    private final CoreEnvironment env;
    private final RingBuffer<ResponseEvent> responseBuffer;
