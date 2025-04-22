	@Test
	public void foo() throws URISyntaxException
		TransportLocal tl = new TransportLocal(new URIish("dummy")
			@Override
			public FetchConnection openFetch() throws TransportException {
				URIish uri = null;
				try {
					uri = new URIish("foo");
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				NoRemoteRepositoryException e = new NoRemoteRepositoryException(uri
				e.initCause(new java.io.EOFException("Short read of block"));
				throw e;
			}
		};
		class BpcTest extends BasePackPushConnection {
			BpcTest(PackTransport packTransport) {
				super(packTransport);
				foo();
			}
			public boolean entryPoint() throws TransportException {
				return readAdvertisedRefs();
			}
			void foo() {
				class Pli extends PacketLineIn {
					public Pli() {
						super(InputStream.nullInputStream());
					}

					@Override
					public String readString() throws IOException {
						throw new EOFException("Short read of block");
					}
				}
				pckIn = new Pli();
			}

		}
		BpcTest bpc = new BpcTest(tl);
		bpc.entryPoint();
	}

