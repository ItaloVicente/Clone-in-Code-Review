
	private static class Negotiate extends HttpAuthMethod {
		private static final GSSManagerFactory GSS_MANAGER_FACTORY = GSSManagerFactory
				.detect();

		private static final Oid OID;
		static {
			try {
			} catch (GSSException e) {
				throw new Error("Cannot create NEGOTIATE oid."
			}
		}

		private final byte[] prevToken;

		public Negotiate(String hdr) {
			super(Type.NEGOTIATE);
			prevToken = Base64.decode(hdr);
		}

		@Override
		void authorize(String user
		}

		@Override
		void configureRequest(HttpConnection conn) throws IOException {
			GSSManager gssManager = GSS_MANAGER_FACTORY.newInstance(conn
					.getURL());
			String host = conn.getURL().getHost();
			try {
				GSSName gssName = gssManager.createName(peerName
						GSSName.NT_HOSTBASED_SERVICE);
				GSSContext context = gssManager.createContext(gssName
						null
				context.requestCredDeleg(true);

				byte[] token = context.initSecContext(prevToken
						prevToken.length);

				conn.setRequestProperty(HDR_AUTHORIZATION
			} catch (GSSException e) {
				throw new IOException(e);
			}
		}
	}
