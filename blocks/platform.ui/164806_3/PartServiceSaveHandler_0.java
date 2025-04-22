		Object client = dirtyPart.getObject();
		IEclipseContext context = dirtyPart.getContext();
		if (client == null || context == null) {
			log("Failed to persist contents of part", //$NON-NLS-1$
					"Failed to persist contents of part ({0}) because the part was not rendered", //$NON-NLS-1$
					dirtyPart.getElementId(), new RuntimeException());
			return false;
		}
