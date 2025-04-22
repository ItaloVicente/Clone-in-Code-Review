	class InternalLocalPushConnection extends BasePackPushConnection {
		private Thread worker;

		InternalLocalPushConnection() throws TransportException {
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

				out_r = new PipedInputStream();
				out_w = new PipedOutputStream(out_r);
			} catch (IOException err) {
				dst.close();
				throw new TransportException(uri, JGitText.get().cannotConnectPipes, err);
			}

				public void run() {
					try {
						final ReceivePack rp = createReceivePack(dst);
						rp.receive(out_r, in_w, System.err);
					} catch (IOException err) {
					} catch (RuntimeException err) {
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

