		final GcLog gcLog = background ? new GcLog(repo) : null;
		if (gcLog != null && gcLog.lock(background)) {
			return Collections.emptyList();
		}

		Callable<Collection<PackFile>> gcTask = () -> {
			try {
				Collection<PackFile> newPacks = doGc();
				if (automatic && tooManyLooseObjects() && gcLog != null) {
					String message = JGitText.get().gcTooManyUnpruned;
					gcLog.write(message);
				}
				return newPacks;
			} catch (IOException | ParseException e) {
				if (background) {
					if (gcLog == null) {
						return Collections.emptyList();
					}
					try {
						gcLog.write(e.getMessage());
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						gcLog.write(sw.toString());
						gcLog.commit();
					} catch (IOException e2) {
						e2.addSuppressed(e);
						LOG.error(e2.getMessage()
					}
				} else {
					throw new JGitInternalException(e.getMessage()
				}
			} finally {
				if (gcLog != null) {
					gcLog.unlock();
				}
			}
			return Collections.emptyList();
		};
		Future<Collection<PackFile>> result = executor.submit(gcTask);
		if (background) {
			return Collections.emptyList();
		}
		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IOException(e);
		}
	}

	private Collection<PackFile> doGc() throws IOException
