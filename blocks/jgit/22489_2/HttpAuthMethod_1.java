		}
		NEGOTIATE {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Negotiate(hdr);
			}
