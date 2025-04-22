	public ExecutionResult execute(ProcessBuilder pb
			throws IOException
		TemporaryBuffer stdout = new TemporaryBuffer.LocalFile(null);
		TemporaryBuffer stderr = new TemporaryBuffer.Heap(1024
		try {
			int rc = runProcess(pb
			return new ExecutionResult(stdout
		} catch (Exception e) {
			stdout.close();
			stderr.close();
			throw e;
		}
	}

