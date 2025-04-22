	static final TransportProtocol PROTO_SSH = new TransportProtocol() {
		private final String[] schemeNames = { "ssh"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<String>(Arrays
						.asList(schemeNames)));

		public String getName() {
			return JGitText.get().transportProtoSSH;
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
			return 22;
		}

		@Override
		public boolean canHandle(Repository local
			if (uri.getScheme() == null) {
				return uri.getHost() != null
					&& uri.getPath() != null
					&& uri.getHost().length() != 0
					&& uri.getPath().length() != 0;
			}
			return super.canHandle(local
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportGitSsh(local
		}
	};
