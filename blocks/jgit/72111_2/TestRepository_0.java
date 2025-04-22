	public void delete(String ref) throws Exception {
		ref = normalizeRef(ref);
		RefUpdate u = db.updateRef(ref);
		switch (u.delete()) {
		case FAST_FORWARD:
		case FORCED:
		case NEW:
		case NO_CHANGE:
			updateServerInfo();
			return;

		default:
			throw new IOException("Cannot delete " + ref + " " + u.getResult());
		}
	}

