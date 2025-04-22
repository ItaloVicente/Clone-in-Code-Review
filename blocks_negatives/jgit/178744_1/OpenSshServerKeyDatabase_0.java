			try {
				if (checkReloadRequired()) {
					if (!Files.exists(path)) {
						resetReloadAttributes();
						return Collections.emptyList();
					}
					LockFile lock = new LockFile(path.toFile());
					if (lock.lock()) {
						try {
							entries = reload(getPath());
						} finally {
							lock.unlock();
						}
					} else {
						LOG.warn(format(SshdText.get().knownHostsFileLockedRead,
								path));
