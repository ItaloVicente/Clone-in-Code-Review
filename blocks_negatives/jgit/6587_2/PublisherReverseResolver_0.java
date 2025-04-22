public class SubscribeState {
	private final Map<String, SubscribedRepository>
			repoSubscriptions = new HashMap<String, SubscribedRepository>();

	private String restartToken;

	private String lastPackId;

	/** @return fast restart token, or null if none. */
	public String getRestartToken() {
		return restartToken;
	}
