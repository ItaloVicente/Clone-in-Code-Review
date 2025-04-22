			WorkingTreeOptions workingTreeOptions = db.getConfig().get(WorkingTreeOptions.KEY);
			final InputStream objectInputStream = db.open(blobId,
					Constants.OBJ_BLOB).openStream();
			switch (workingTreeOptions.getAutoCRLF()) {
			case INPUT:
			case FALSE:
				return objectInputStream;
			case TRUE:
			default:
				return new AutoCRLFInputStream(objectInputStream, true);
			}
