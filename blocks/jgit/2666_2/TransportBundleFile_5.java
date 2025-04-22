	static final TransportProtocol PROTO_BUNDLE = new TransportProtocol() {
		private final String[] schemeNames = { "bundle"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<String>(Arrays
						.asList(schemeNames)));

		@Override
		public String getName() {
			return JGitText.get().transportProtoBundleFile;
