	private void cloneSubmodules(Repository clonedRepo) {
		SubmoduleInitCommand init = new SubmoduleInitCommand(clonedRepo);
		if (init.call().isEmpty())
			return;

		SubmoduleUpdateCommand update = new SubmoduleUpdateCommand(clonedRepo);
		configure(update);
		update.setProgressMonitor(monitor);
		update.call();
	}

