	static final TransportProtocol PROTO_SFTP = new TransportProtocol() {
		public String getName() {
			return JGitText.get().transportProtoSFTP;
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
			return 22;
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportSftp(local
		}
	};
