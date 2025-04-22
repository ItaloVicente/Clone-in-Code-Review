
	private static class FailingBasePackConnection extends BasePackConnection {
		private final Throwable noRepositoryCauseException;
		private final TransportException noRepositoryException;

		FailingBasePackConnection(URIish uri
								  Throwable noRepositoryCauseException
								  IOException readException
								  TransportException noRepositoryException) {
			super(new TransportLocal(uri
			this.noRepositoryCauseException = noRepositoryCauseException;
			this.noRepositoryException = noRepositoryException;
			class Pli extends PacketLineIn {
				@SuppressWarnings("InputStreamSlowMultibyteRead")
				public Pli() {
					super(new InputStream() {
						@Override
						public int read() throws IOException {
							throw readException;
						}
					});
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
