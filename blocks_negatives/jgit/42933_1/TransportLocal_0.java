	class InternalLocalFetchConnection extends BasePackFetchConnection {
		private Thread worker;

		InternalLocalFetchConnection() throws TransportException {
			super(TransportLocal.this);

			final Repository dst;
			try {
				dst = new RepositoryBuilder().setGitDir(remoteGitDir).build();
			} catch (IOException err) {
				throw new TransportException(uri, JGitText.get().notAGitDirectory);
			}

			final PipedInputStream in_r;
			final PipedOutputStream in_w;

			final PipedInputStream out_r;
			final PipedOutputStream out_w;
			try {
				in_r = new PipedInputStream();
				in_w = new PipedOutputStream(in_r);

				out_r = new PipedInputStream() {
					{
						buffer = new byte[MIN_CLIENT_BUFFER];
					}
				};
				out_w = new PipedOutputStream(out_r);
			} catch (IOException err) {
				dst.close();
				throw new TransportException(uri, JGitText.get().cannotConnectPipes, err);
			}

				public void run() {
					try {
						final UploadPack rp = createUploadPack(dst);
						rp.upload(out_r, in_w, null);
					} catch (IOException err) {
						err.printStackTrace();
					} catch (RuntimeException err) {
						err.printStackTrace();
					} finally {
						try {
							out_r.close();
						} catch (IOException e2) {
						}

						try {
							in_w.close();
						} catch (IOException e2) {
						}

						dst.close();
					}
				}
			};
			worker.start();

			init(in_r, out_w);
			readAdvertisedRefs();
		}

		@Override
		public void close() {
			super.close();

			if (worker != null) {
				try {
					worker.join();
				} catch (InterruptedException ie) {
				} finally {
					worker = null;
				}
			}
		}
	}

