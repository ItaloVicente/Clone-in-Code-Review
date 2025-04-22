		if (client == null) {
			log("Failed to persist contents of part", //$NON-NLS-1$
					"Failed to persist contents of part ({0}) because Part#getObject() returned null", //$NON-NLS-1$
					dirtyPart.getElementId(), new RuntimeException());
			return false;
		}
