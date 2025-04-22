	public static <S extends IWorkbench> IValueProperty<S, IWorkbenchWindow> activeWindow() {
		return new ActiveWindowProperty<>();
	}

	public static <S extends IPageService> IValueProperty<S, IWorkbenchPage> activePage() {
		return new ActivePageProperty<>();
	}

	public static <S extends IPartService> IValueProperty<S, IWorkbenchPartReference> activePartReference() {
		return new ActivePartProperty<>();
	}

	public static <S extends IPartService> IValueProperty<S, IEditorReference> activeEditorReference() {
		return WorkbenchProperties.<S>activePartReference().value(Properties.convertedValue(IEditorReference.class,
						part -> part instanceof IEditorReference ? (IEditorReference) part : null));
	}

	public static <S extends IEditorPart> IValueProperty<S, IEditorInput> editorInput() {
		return new EditorInputProperty<>();
	}
