	private void writeNewRef(String name
		RefUpdate updateRef = db.updateRef(name);
		updateRef.setNewObjectId(value);
		assertEquals(RefUpdate.Result.NEW
	}

