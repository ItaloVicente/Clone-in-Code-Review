		ObjectLoader ldr = openObjectNoRestore(curs
		if (ldr == null && restoreFromSelfOrAlternate(objectId
			ldr = openObjectNoRestore(curs
		}
		return ldr;
	}

	private ObjectLoader openObjectNoRestore(WindowCursor curs
			throws IOException {
