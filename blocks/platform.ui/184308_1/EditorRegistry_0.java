	private Map<String, IEditorDescriptor> mapIDtoInternalEditor = initialIdToEditorMap(10);

	private static class LazyOSEditorsHolder {
		static final Map<String, IEditorDescriptor> INSTANCE = getIDtoOSEditors();
	}

	private static class LazySortedEditorsFromOSHolder {
		static final IEditorDescriptor[] INSTANCE = getStaticSortedEditorsFromOS();
	}
