	public int runIfPresent(Repository repository
			String[] args) throws JGitInternalException {
		return runIfPresent(repository
				null);
	}

	public int runIfPresent(Repository repository
			String[] args
			String stdinArgs) throws JGitInternalException {
		return 0;
	}

	public File tryFindHook(Repository repository
		final File hookFile = new File(new File(repository.getDirectory()
				"hooks")
		return hookFile.isFile() ? hookFile : null;
	}

	protected int runProcess(ProcessBuilder hookProcessBuilder
			OutputStream outRedirect
			throws IOException
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		Process process = null;
		IOException ioException = null;
		try {
			process = hookProcessBuilder.start();
			final Callable<Void> errorGobbler = new StreamGobbler(
					process.getErrorStream()
			final Callable<Void> outputGobbler = new StreamGobbler(
					process.getInputStream()
			executor.submit(errorGobbler);
			executor.submit(outputGobbler);
			if (stdinArgs != null) {
				final PrintWriter stdinWriter = new PrintWriter(
						process.getOutputStream());
				stdinWriter.print(stdinArgs);
				stdinWriter.flush();
				stdinWriter.close();
			}
			return process.waitFor();
		} catch (IOException e) {
			ioException = e;
		} finally {
			shutdownAndAwaitTermination(executor);
			if (process != null) {
				try {
					process.waitFor();
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
				try {
					process.getErrorStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				try {
					process.getInputStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				try {
					process.getOutputStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				process.destroy();
			}
		}
		throw ioException;
	}

	private static boolean shutdownAndAwaitTermination(ExecutorService pool) {
		boolean hasShutdown = true;
		try {
			if (!pool.awaitTermination(5
				if (!pool.awaitTermination(5
					hasShutdown = false;
			}
		} catch (InterruptedException ie) {
			pool.shutdownNow();
			Thread.currentThread().interrupt();
			hasShutdown = false;
		}
		return hasShutdown;
	}

