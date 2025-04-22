
	private static class FailingBasePackConnection extends BasePackConnection {
		private final Throwable noRepositoryCauseException;
		private final TransportException noRepositoryException;

		FailingBasePackConnection(URIish uri
			super(new TransportLocal(uri
			this.noRepositoryCauseException = noRepositoryCauseException;
			this.noRepositoryException = noRepositoryException;
			class Pli extends PacketLineIn {
				public Pli() {
					super(new InputStream() {
						@Override
						public int read() throws IOException {
							return 0;
						}
					});
				}

				@Override
				public String readString() throws IOException {
					throw readStringException;
				}
			}
			pckIn = new Pli();
		}

		@Override
		protected TransportException noRepository() {
			noRepositoryException.initCause(noRepositoryCauseException);
			return noRepositoryException;
		}

		public boolean callReadAdvertisedRefs() throws TransportException {
			return readAdvertisedRefs();
		}
	}
