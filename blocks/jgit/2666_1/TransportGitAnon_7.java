	static final TransportProtocol PROTO_GIT = new TransportProtocol() {
		public String getName() {
			return JGitText.get().transportProtoGitAnon;
		}

		public Set<String> getSchemes() {
		}

		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.PORT));
		}

		public int getDefaultPort() {
			return GIT_PORT;
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportGitAnon(local
		}
	};
