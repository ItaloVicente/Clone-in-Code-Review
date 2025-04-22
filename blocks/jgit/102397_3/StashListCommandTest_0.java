
	private RefUpdate newStashUpdate(ObjectId newId) throws Exception {
		RefUpdate ru = db.updateRef(Constants.R_STASH);
		ru.setNewObjectId(newId);
		ru.setForceRefLog(true);
		return ru;
	}
