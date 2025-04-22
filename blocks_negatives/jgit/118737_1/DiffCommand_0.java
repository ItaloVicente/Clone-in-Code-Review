		final DiffFormatter diffFmt;
		if (out != null && !showNameAndStatusOnly)
			diffFmt = new DiffFormatter(new BufferedOutputStream(out));
		else
			diffFmt = new DiffFormatter(NullOutputStream.INSTANCE);
		diffFmt.setRepository(repo);
		diffFmt.setProgressMonitor(monitor);
		try {
