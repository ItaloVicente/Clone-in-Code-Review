		ObjectLoader ldr = openObjectWithoutRestoring(curs
		if (ldr == null && restoreFromSelfOrAlternate(objectId
			ldr = openObjectWithoutRestoring(curs
		}
		return ldr;
	}

	private ObjectLoader openObjectWithoutRestoring(WindowCursor curs
			throws IOException {
