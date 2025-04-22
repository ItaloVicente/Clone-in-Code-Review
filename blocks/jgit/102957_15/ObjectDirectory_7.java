		if (shallowHandler == null) {
			shallowCommitsIds = Collections.emptySet();
		}
		else {
			shallowHandler.lock();
			final List<ObjectId> list = shallowHandler.read();
			shallowHandler.unlock(false);
			shallowCommitsIds = new HashSet<>(list);
