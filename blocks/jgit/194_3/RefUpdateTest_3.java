
	private void writeReflog(Repository db
			String msg
		RefDirectory refs = (RefDirectory) db.getRefDatabase();
		RefDirectoryUpdate update = refs.newUpdate(refName
		update.setOldObjectId(oldId);
		update.setNewObjectId(newId);
		refs.log(update
	}
