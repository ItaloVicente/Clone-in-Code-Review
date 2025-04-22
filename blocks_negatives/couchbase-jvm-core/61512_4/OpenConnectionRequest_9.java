/**
 * Initiate logical DCP channel.
 *
 * @author Sergey Avseyev
 * @since 1.1.0
 */
@InterfaceStability.Experimental
@InterfaceAudience.Private
public class OpenConnectionRequest extends AbstractDCPRequest {
    /**
     * Type of connection the server have to initiate for the client.
     * For example, if the client wants to pull the data from server, it need to
     * choose {@link ConnectionType#CONSUMER}.
     */
    private final ConnectionType type;

    /**
     * The connection name can be used to get statistics about the connection state
     * as well as other useful debugging information. If a connection already exists
     * on the Producer with the same name then the old connection is closed and
     * a new one is opened.
     */
