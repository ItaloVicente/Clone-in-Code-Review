		if (detectRenames) {
			RenameDetector rd = new RenameDetector(db);
			if (renameLimit != null)
				rd.setRenameLimit(renameLimit.intValue());
			rd.addAll(files);
			files = rd.compute(new TextProgressMonitor());
		}
