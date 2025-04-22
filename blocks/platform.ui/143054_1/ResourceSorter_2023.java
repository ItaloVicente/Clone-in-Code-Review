	public static final int NAME = 1;

	public static final int TYPE = 2;

	private int criteria;

	public ResourceSorter(int criteria) {
		super();
		this.criteria = criteria;
	}

	protected int classComparison(Object element) {
		if (element instanceof IResource) {
			return 2;
		}
		return 0;
	}

	@Override
