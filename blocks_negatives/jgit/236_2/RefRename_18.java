	ObjectId getObjectId() {
		return oldFromDelete.getOldObjectId();
	}

	Repository getRepository() {
		return oldFromDelete.getRepository();
	}

	PersonIdent getRefLogIdent() {
		return newToUpdate.getRefLogIdent();
	}
