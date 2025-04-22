			lock.unlock();
		}
	}

	private boolean isNameConflicting() throws IOException {
		final String myName = getName();
		final int lastSlash = myName.lastIndexOf('/');
		if (lastSlash > 0)
			if (db.getRepository().getRef(myName.substring(0, lastSlash)) != null)
				return true;

		final String rName = myName + "/";
		for (Ref r : db.getAllRefs().values()) {
			if (r.getName().startsWith(rName))
				return true;
