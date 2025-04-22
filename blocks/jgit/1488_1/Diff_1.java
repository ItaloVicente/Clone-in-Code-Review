		diffFmt.setRepository(db);
		try {
			diffFmt.setProgressMonitor(new TextProgressMonitor());
			diffFmt.setPathFilter(pathFilter);
			if (detectRenames != null)
				diffFmt.setDetectRenames(detectRenames.booleanValue());
			if (renameLimit != null && diffFmt.isDetectRenames()) {
				RenameDetector rd = diffFmt.getRenameDetector();
				rd.setRenameLimit(renameLimit.intValue());
			}
