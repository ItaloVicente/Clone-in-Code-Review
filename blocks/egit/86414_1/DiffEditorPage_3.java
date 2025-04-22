
	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		DiffViewer viewer = new DiffViewer(parent, ruler, getOverviewRuler(),
				isOverviewRulerVisible(), styles);
		getSourceViewerDecorationSupport(viewer);
		ProjectionSupport projector = new ProjectionSupport(viewer,
				getAnnotationAccess(), getSharedColors());
		projector.install();
		return viewer;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setDocumentProvider(new DiffDocumentProvider());
		setSourceViewerConfiguration(
				new DiffViewer.Configuration(getPreferenceStore()));
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		if (input instanceof DiffEditorInput) {
			setFolding();
		} else if (input instanceof CommitEditorInput) {
			formatDiff();
		}
	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, ITextEditorActionConstants.GROUP_COPY,
				ITextEditorActionConstants.SELECT_ALL);
		menu.remove(ITextEditorActionConstants.SHIFT_RIGHT);
		menu.remove(ITextEditorActionConstants.SHIFT_LEFT);
	}


	@Override
	public void initialize(FormEditor editor) {
		formEditor = editor;
	}

	@Override
	public FormEditor getEditor() {
		return formEditor;
	}

	@Override
	public IManagedForm getManagedForm() {
		return null;
	}

	@Override
	public void setActive(boolean active) {
	}

	@Override
	public boolean isActive() {
		return this.equals(formEditor.getActivePageInstance());
	}

	@Override
	public boolean canLeaveThePage() {
		return true;
	}

	@Override
	public Control getPartControl() {
		return textControl;
	}

	@Override
	public String getId() {
		return pageId;
	}

	@Override
	public int getIndex() {
		return pageIndex;
	}

	@Override
	public void setIndex(int index) {
		pageIndex = index;
	}

	@Override
	public boolean isEditor() {
		return true;
	}

	@Override
	public boolean selectReveal(Object object) {
		if (object instanceof IMarker) {
			IDE.gotoMarker(this, (IMarker) object);
			return true;
		}
		return false;
	}


	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		Control[] children = parent.getChildren();
		textControl = children[children.length - 1];
	}


	private void setFolding() {
		ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
		IDocument document = viewer.getDocument();
		if (document instanceof DiffDocument) {
			viewer.enableProjection();
			FileDiffRange[] ranges = ((DiffDocument) document).getFileRanges();
			if (ranges == null || ranges.length <= 1) {
				return;
			}
			Map<Annotation, Position> newAnnotations = new HashMap<>();
			for (FileDiffRange range : ranges) {
				newAnnotations.put(new ProjectionAnnotation(),
						new Position(range.getStartOffset(),
								range.getEndOffset() - range.getStartOffset()));
			}
			viewer.getProjectionAnnotationModel().modifyAnnotations(
					currentAnnotations, newAnnotations, null);
			currentAnnotations = newAnnotations.keySet()
					.toArray(new Annotation[newAnnotations.size()]);
		} else {
			viewer.disableProjection();
		}
	}

