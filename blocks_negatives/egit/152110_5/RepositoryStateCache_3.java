		ObjectId head = ObjectId.zeroId();
		String name = null;
		Ref r = null;
		try {
			r = repository.exactRef(Constants.HEAD);
		} catch (IOException e) {
			Activator.logError(e.getLocalizedMessage(), e);
		}
		ref[0] = r;
		if (r != null) {
			if (r.isSymbolic()) {
				name = r.getTarget().getName();
