	private String fUpperListLabel;

	private String fLowerListLabel;

	private Comparator fLowerListComparator = null;

	private ILabelProvider fQualifierRenderer;

	private Object[] fElements = new Object[0];

	private Table fLowerList;

	private Object[] fQualifierElements;

	public TwoPaneElementSelector(Shell parent, ILabelProvider elementRenderer, ILabelProvider qualifierRenderer) {
		super(parent, elementRenderer);
		setSize(50, 15);
		setAllowDuplicates(false);
		fQualifierRenderer = qualifierRenderer;
	}

	public void setUpperListLabel(String label) {
		fUpperListLabel = label;
	}

	public void setLowerListLabel(String label) {
		fLowerListLabel = label;
	}

	public void setLowerListComparator(Comparator comparator) {
		fLowerListComparator = comparator;
	}

	public void setElements(Object[] elements) {
		fElements = elements;
	}

	@Override
