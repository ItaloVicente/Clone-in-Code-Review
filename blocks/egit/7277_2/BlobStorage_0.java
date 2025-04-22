			WorkingTreeOptions workingTreeOptions = db.getConfig().get(WorkingTreeOptions.KEY);
			switch (workingTreeOptions.getAutoCRLF()) {
			case INPUT:
			case FALSE:
				return db.open(blobId, Constants.OBJ_BLOB).openStream();
			case TRUE:
			default:
				return new AutoCRLFInputStream(db.open(blobId, Constants.OBJ_BLOB).openStream(), true);
			}
