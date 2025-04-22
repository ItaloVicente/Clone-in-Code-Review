			CredentialsProvider prevProvider = null;
			try {
				if (credentialsProvider != null) {
					prevProvider = LfsFactory.getCredentialsProvider();
					LfsFactory.setCredentialsProvider(credentialsProvider);
				}
				DirCache dc = clonedRepo.lockDirCache();
				DirCacheCheckout co = new DirCacheCheckout(clonedRepo
						commit.getTree());
				co.setProgressMonitor(monitor);
				co.checkout();
			} finally {
				if (credentialsProvider != null) {
					LfsFactory.setCredentialsProvider(prevProvider);
				}
			}
