		GC gc = new GC(this);
		gc.setPackConfig(new PackConfig(this));
		gc.setProgressMonitor(monitor);
		gc.setAuto(true);
		try {
			gc.gc();
		} catch (ParseException | IOException e) {
			throw new JGitInternalException(JGitText.get().gcFailed, e);
