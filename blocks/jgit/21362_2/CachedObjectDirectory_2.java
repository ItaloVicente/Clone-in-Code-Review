		if (unpackedObjects.contains(id)) {
			ObjectLoader ldr = wrapped.openLooseObject(curs
			if (ldr != null)
				return ldr;
			unpackedObjects = scanLoose();
		}
		return null;
