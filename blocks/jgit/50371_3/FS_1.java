		InputStream in = (stdinArgs == null) ? null : new ByteArrayInputStream(
				stdinArgs.getBytes(Constants.CHARACTER_ENCODING));
		return runProcess(processBuilder
	}

	public int runProcess(ProcessBuilder processBuilder
			OutputStream outRedirect
			InputStream inRedirect) throws IOException
			InterruptedException {
