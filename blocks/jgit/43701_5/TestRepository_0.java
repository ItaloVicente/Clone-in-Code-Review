	public void reset(AnyObjectId id) throws Exception {
		RefUpdate ru = db.updateRef(Constants.HEAD
		ru.setNewObjectId(id);
		RefUpdate.Result result = ru.forceUpdate();
		switch (result) {
			case FAST_FORWARD:
			case FORCED:
			case NEW:
			case NO_CHANGE:
				break;
			default:
				throw new IOException(String.format(
						"Checkout \"%s\" failed: %s"
		}
	}

	public void reset(String name) throws Exception {
		RefUpdate.Result result;
		Ref ref = db.getRef(name);
		if (ref != null)
			result = db.updateRef(Constants.HEAD).link(ref.getName());
		else {
			ObjectId id = db.resolve(name);
			if (id == null)
				throw new IOException("Not a revision: " + name);
			RefUpdate ru = db.updateRef(Constants.HEAD
			ru.setNewObjectId(id);
			result = ru.forceUpdate();
		}
		switch (result) {
			case FAST_FORWARD:
			case FORCED:
			case NEW:
			case NO_CHANGE:
				break;
			default:
				throw new IOException(String.format(
						"Checkout \"%s\" failed: %s"
		}
	}

