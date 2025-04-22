	static final TransportProtocol PROTO_HTTP = new TransportProtocol() {
		private final String[] schemeNames = { "http"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<String>(Arrays
						.asList(schemeNames)));

		public String getName() {
			return JGitText.get().transportProtoHTTP;
		}

		public Set<String> getSchemes() {
			return schemeSet;
		}

		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		public int getDefaultPort() {
			return 80;
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportHttp(local
		}
	};

	static final TransportProtocol PROTO_FTP = new TransportProtocol() {
		public String getName() {
			return JGitText.get().transportProtoFTP;
		}

		public Set<String> getSchemes() {
		}

		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		public int getDefaultPort() {
			return 21;
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportHttp(local
		}
	};
