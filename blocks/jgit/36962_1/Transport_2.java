		if (check && objectChecker == null)
			setObjectChecker(new ObjectChecker());
		else if (!check && objectChecker != null)
			setObjectChecker(null);
	}

	public ObjectChecker getObjectChecker() {
		return objectChecker;
	}

	public void setObjectChecker(ObjectChecker impl) {
		objectChecker = impl;
