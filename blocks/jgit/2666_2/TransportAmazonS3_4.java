	static final TransportProtocol PROTO_S3 = new TransportProtocol() {
		public String getName() {
			return "Amazon S3";
		}

		public Set<String> getSchemes() {
			return Collections.singleton(S3_SCHEME);
		}

		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.HOST
		}

		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.PASS));
		}

		public Transport open(Repository local
				throws NotSupportedException {
			return new TransportAmazonS3(local
		}
	};
