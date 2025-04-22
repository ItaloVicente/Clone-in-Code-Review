		if (activePart instanceof ISaveablePart) {
			return (ISaveablePart) activePart;
		}

		ISaveablePart part = Util.getAdapter(activePart, ISaveablePart.class);
		if (part != null)
