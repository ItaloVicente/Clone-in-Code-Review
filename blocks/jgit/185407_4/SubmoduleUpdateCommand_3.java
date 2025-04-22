						CredentialsProvider prevProvider = null;
						try {
							if (credentialsProvider != null) {
								prevProvider = LfsFactory.getCredentialsProvider();
								LfsFactory.setCredentialsProvider(credentialsProvider);
							}
							DirCacheCheckout co = new DirCacheCheckout(
									submoduleRepo
									commit.getTree());
							co.setFailOnConflict(true);
							co.setProgressMonitor(monitor);
							co.checkout();
						} finally {
							if (credentialsProvider != null) {
								LfsFactory.setCredentialsProvider(prevProvider);
							}
						}
