	public static boolean isDirtyStateSupported(IWorkbenchPart part) {
		if (part instanceof ISecondarySaveableSource) {
			return ((ISecondarySaveableSource) part).isDirtyStateIndicationSupported();
		}
		return true;
	}

