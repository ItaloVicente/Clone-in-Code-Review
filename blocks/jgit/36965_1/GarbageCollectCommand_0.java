			if (repo instanceof FileRepository) {
				GC gc = new GC((FileRepository) repo);
				gc.setPackConfig(pconfig);
				gc.setProgressMonitor(monitor);
				if (this.expire != null)
					gc.setExpire(expire);

				try {
					gc.gc();
					return toProperties(gc.getStatistics());
				} catch (ParseException e) {
					throw new JGitInternalException(JGitText.get().gcFailed
				}
			} else if (repo instanceof DfsRepository) {
				DfsGarbageCollector gc =
					new DfsGarbageCollector((DfsRepository) repo);
				gc.setPackConfig(pconfig);
				gc.pack(monitor);
				return new Properties();
			} else {
				throw new UnsupportedOperationException(MessageFormat.format(
						JGitText.get().unsupportedGC
						repo.getClass().toString()));
			}
