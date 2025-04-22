/**
 * The {@link DCPService} is composed of and manages {@link DCPEndpoint}s.
 *
 * @author Sergey Avseyev
 * @since 1.1.0
 */
public class DCPService extends AbstractService {
    /**
     * The endpoint selection strategy.
     */
    private static final SelectionStrategy STRATEGY = new FirstConnectedSelectionStrategy();
