		if (check && objectChecker == null)
			setObjectChecker(new ObjectChecker());
		else if (!check && objectChecker != null)
			setObjectChecker(null);
	}

	public void setObjectChecker(ObjectChecker impl) {
		objectChecker = impl;
