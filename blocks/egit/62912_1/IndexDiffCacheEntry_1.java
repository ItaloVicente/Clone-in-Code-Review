				Job next = reloadJob;
				if (next != null) {
					try {
						next.join();
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
				}
				if (Activator.getDefault().isDebugging()) {
					final long refresh = System.currentTimeMillis();
					Activator.logInfo("Diff took " + (refresh - start) //$NON-NLS-1$
							+ " ms for " + repositoryName); //$NON-NLS-1$

				}
