	public static E4PartWrapper getE4PartWarapper(MPart part) {
		if (part != null) {
			if (part.getTransientData().get(E4_WRAPPER_KEY) instanceof E4PartWrapper) {
				return (E4PartWrapper) part.getTransientData().get(E4_WRAPPER_KEY);
			}
			E4PartWrapper newWrapper = new E4PartWrapper(part);
			part.getTransientData().put(E4_WRAPPER_KEY, newWrapper);
			return newWrapper;
		}
		return null;
	}

