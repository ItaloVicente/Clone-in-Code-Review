		reader = db.newObjectReader();

		DiffConfig dc = db.getConfig().get(DiffConfig.KEY);
		if (dc.isNoPrefix()) {
			setOldPrefix("");
			setNewPrefix("");
		}
		setDetectRenames(dc.isRenameDetectionEnabled());
