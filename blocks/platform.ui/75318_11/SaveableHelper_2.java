	public static boolean isDirtyStateSupported(IWorkbenchPart part) {
		if (part instanceof ISecondarySaveableSource) {
			return ((ISecondarySaveableSource) part).isDirtyStateSupported();
		}
		return isSaveable(part);
	}

