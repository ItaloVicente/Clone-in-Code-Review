	public boolean canRunHooks() {
		return false;
	}

	public int runIfPresent(Repository repository
			String[] args) throws UnsupportedOperationException {
		return runIfPresent(repository
	}

	public int runIfPresent(Repository repository
			String[] args
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	protected File tryFindHook(Repository repository
		final File gitDir = repository.getDirectory();
		final File[] hookDirCandidates = gitDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory()
			}
		});
		if (hookDirCandidates.length < 1)
			return null;

		final File[] matchingHooks = hookDirCandidates[0]
				.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.isFile()
								&& pathname.getName().equals(hook.getName());
					}
				});
		if (matchingHooks.length < 1)
			return null;
		return matchingHooks[0];
	}

	protected int runHook(ProcessBuilder hookProcessBuilder
			PrintStream outRedirect
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		try {
			final Process process = hookProcessBuilder.start();
			final Runnable errorGobbler = new StreamGobbler(
					process.getErrorStream()
			final Runnable outputGobbler = new StreamGobbler(
					process.getInputStream()
			executor.submit(errorGobbler);
			executor.submit(outputGobbler);
			return process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			shutdownAndAwaitTermination(executor);
		}
		return -1;
	}

	static boolean shutdownAndAwaitTermination(ExecutorService pool) {
		boolean hasShutdown = true;
		try {
			if (!pool.awaitTermination(5
				if (!pool.awaitTermination(5
					hasShutdown = false;
				}
			}
		} catch (InterruptedException ie) {
			pool.shutdownNow();
			Thread.currentThread().interrupt();
			hasShutdown = false;
		}
		return hasShutdown;
	}

