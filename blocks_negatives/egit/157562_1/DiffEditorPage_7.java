
	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		DiffViewer viewer = new DiffViewer(parent, ruler, getOverviewRuler(),
				isOverviewRulerVisible(), styles) {
			@Override
			protected void setFont(Font font) {
			}
		};
		getSourceViewerDecorationSupport(viewer);
		ProjectionSupport projector = new ProjectionSupport(viewer,
				getAnnotationAccess(), getSharedColors());
		projector.install();
		viewer.getTextWidget().addCaretListener(event -> {
			if (outlinePage != null) {
				FileDiffRegion region = getFileDiffRange(event.caretOffset);
				if (region != null && !region.equals(currentFileDiffRange)) {
					currentFileDiffRange = region;
					outlinePage.setSelection(new StructuredSelection(region));
				} else {
					currentFileDiffRange = region;
				}
			}
		});
		return viewer;
	}

	@Override
	protected IVerticalRulerColumn createLineNumberRulerColumn() {
		lineNumberColumn = new OldNewLogicalLineNumberRulerColumn(
				plainLineNumbers);
		initializeLineNumberRulerColumn(lineNumberColumn);
		return lineNumberColumn;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		overviewStore = new ThemePreferenceStore();
		setPreferenceStore(new ChainedPreferenceStore(new IPreferenceStore[] {
				overviewStore, EditorsUI.getPreferenceStore() }));
		setDocumentProvider(new DiffDocumentProvider());
		setSourceViewerConfiguration(
				new DiffViewer.Configuration(getPreferenceStore()) {
					@Override
					public IReconciler getReconciler(
							ISourceViewer sourceViewer) {
						return null;
					}
				});
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		if (input instanceof DiffEditorInput) {
			setFolding();
			FileDiffRegion region = getFileDiffRange(0);
			currentFileDiffRange = region;
			setOverviewAnnotations();
		} else if (input instanceof CommitEditorInput) {
			formatDiff((CommitEditorInput) input);
			currentFileDiffRange = null;
		}
		if (outlinePage != null) {
			outlinePage.setInput(getDocumentProvider().getDocument(input));
			if (currentFileDiffRange != null) {
				outlinePage.setSelection(
						new StructuredSelection(currentFileDiffRange));
			}
		}
	}

