		ObjectLoader ldr;

		ldr = openObject2(curs, objectName, objectId);
		if (ldr != null) {
			return ldr;
		}
		for (final ObjectDatabase alt : getAlternates()) {
			ldr = alt.openObjectImpl2(curs, objectName, objectId);
			if (ldr != null) {
				return ldr;
			}
		}
		return null;
