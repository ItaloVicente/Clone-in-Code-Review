	@Override
	protected void run() throws Exception {
		diffFmt.setRepository(db);
		try {
			diffFmt.setPathFilter(pathFilter);
			if (detectRenames != null)
				diffFmt.setDetectRenames(detectRenames.booleanValue());
			if (renameLimit != null && diffFmt.isDetectRenames()) {
				RenameDetector rd = diffFmt.getRenameDetector();
				rd.setRenameLimit(renameLimit.intValue());
			}

			super.run();
		} finally {
			diffFmt.release();
		}
	}

