	public DiffViewer(Composite parent, IVerticalRuler ruler, int styles) {
		this(parent, ruler, null, false, styles);
	}

	public DiffViewer(Composite parent, IVerticalRuler ruler,
			IOverviewRuler overviewRuler, boolean showsAnnotationOverview,
			int styles) {
		super(parent, ruler, overviewRuler, showsAnnotationOverview, styles);
