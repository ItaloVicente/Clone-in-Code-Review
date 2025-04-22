}

/**
 * Job reducing label events to prevent unnecessary (i.e. redundant) event
 * processing
 */
class LabelEventJob extends Job {

	/**
	 * Constant defining the waiting time (in milliseconds) until an event is
	 * fired
	 */
	private static final long DELAY = 100L;


	/**
	 * Get the LabelEventJob singleton
	 *
	 * @return the LabelEventJob singleton
	 */
	static LabelEventJob getInstance() {
		return instance;
	}

	private LabelEventJob(final String name) {
		super(name);
		setSystem(true);
	}

	private GitLightweightDecorator glwDecorator;

	/**
	 * Post a label event
	 *
	 * @param decorator
	 *            The GitLightweightDecorator that is used to fire a
	 *            LabelProviderChangedEvent
	 */
	void postLabelEvent(final GitLightweightDecorator decorator) {
		if (glwDecorator == null)
			glwDecorator = decorator;
		if (getState() == SLEEPING || getState() == WAITING)
			cancel();
		schedule(DELAY);
	}
