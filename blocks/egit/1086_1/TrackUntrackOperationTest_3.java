		assertTrackedState(fileArr, true);

		UntrackOperation utrop = new UntrackOperation(containers);
		utrop.execute(new NullProgressMonitor());

		assertTrackedState(fileArr, false);
