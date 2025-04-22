	public DiffViewer(Composite parent, IVerticalRuler ruler, int styles,
			boolean showCursorLine) {
		super(parent, ruler, styles);
		setDocument(new Document());
		SourceViewerDecorationSupport support = new SourceViewerDecorationSupport(
				this, null, null, EditorsUI.getSharedTextColors());
		if (showCursorLine) {
			support.setCursorLinePainterPreferenceKeys(
					AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE,
					AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR);
		}
		support.install(EditorsUI.getPreferenceStore());
		if (ruler instanceof CompositeRuler) {
			lineNumberRuler = new LineNumberRulerColumn();
			((CompositeRuler) ruler).addDecorator(0, lineNumberRuler);
		}
