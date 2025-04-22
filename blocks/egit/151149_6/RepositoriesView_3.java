	private final ISelectionListener selectionChangedListener = (part,
			selection) -> {
		if (!((Boolean) reactOnSelection.getValue()).booleanValue()
				|| part == RepositoriesView.this) {
			return;
		}

		if (part instanceof IEditorPart) {
			IEditorInput input = ((IEditorPart) part).getEditorInput();
			if (input instanceof IFileEditorInput) {
				reactOnSelection(new StructuredSelection(
						((IFileEditorInput) input).getFile()));
			} else if (input instanceof IURIEditorInput) {
				reactOnSelection(new StructuredSelection(input));
			}

		} else {
			reactOnSelection(selection);
		}
	};

	private final IStateListener reactOnSelectionListener = (state,
			oldValue) -> {
		if (((Boolean) state.getValue()).booleanValue()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
				ISelectionService service = CommonUtils
						.getService(getViewSite(), ISelectionService.class);
				if (service == null) {
					return;
				}
				ISelection currentSelection = service.getSelection();
				if (currentSelection == null || currentSelection.isEmpty()) {
					return;
				}
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window == null) {
					return;
				}
				IWorkbenchPart part = window.getPartService().getActivePart();
				if (part != null) {
					selectionChangedListener.selectionChanged(part,
							currentSelection);
				}
			});
		}
	};

	private State branchHierarchy;
