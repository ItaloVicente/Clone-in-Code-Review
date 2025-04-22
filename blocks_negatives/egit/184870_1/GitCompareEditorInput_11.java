				final RevCommit compareCommit;
				if (compareVersion == null) {
					compareCommit = null;
				} else {
					try {
						compareCommit = rw.parseCommit(
								repository.resolve(compareVersion));
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					}
				}
				if (monitor.isCanceled())
					throw new InterruptedException();
