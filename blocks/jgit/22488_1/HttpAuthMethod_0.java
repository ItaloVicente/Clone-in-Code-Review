	public enum Type {
		NONE {
			@Override
			public HttpAuthMethod method(String hdr) {
				return None.INSTANCE;
			}
		}
		BASIC {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Basic();
			}
		}
		DIGEST {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Digest(hdr);
			}
		};
		public abstract HttpAuthMethod method(String hdr);
	}

