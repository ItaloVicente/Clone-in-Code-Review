	private Button useTopic;

	private Label topicLabel;

	private Text topicText;

	private Set<String> knownRemoteRefs = new TreeSet<>(
			String.CASE_INSENSITIVE_ORDER);

	@SuppressWarnings("serial")
	private Map<String, String> topicProposals = new LinkedHashMap<String, String>(
			30, 0.75f, true) {

		private static final int TOPIC_PROPOSALS_MAXIMUM = 20;

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
			return size() > TOPIC_PROPOSALS_MAXIMUM;
		}
	};

