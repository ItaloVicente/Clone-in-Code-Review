	private Row defaultRowSettings = new Row(false);

	private Row defaultColSettings = new Row(true);

	int horizontalSpacing = 5;

	int verticalSpacing = 5;

	public int marginWidth = 5;

	public int marginHeight = 5;

	private int numCols;

	private List cols;

	private List rows = new ArrayList(16);

	private GridInfo gridInfo = new GridInfo();

	private int[] cachedRowMin = null;

	private int[] cachedColMin = null;

	public static int cacheMisses;

	public static int cacheHits;

	private LayoutCache cache = new LayoutCache();


	public CellLayout(int numCols) {
		super();
		this.numCols = numCols;
		cols = new ArrayList(numCols == 0 ? 3 : numCols);
	}

	public CellLayout setSpacing(int horizontalSpacing, int verticalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
		this.verticalSpacing = verticalSpacing;

		return this;
	}

	public CellLayout setSpacing(Point newSpacing) {
		horizontalSpacing = newSpacing.x;
		verticalSpacing = newSpacing.y;
		return this;
	}

	public Point getSpacing() {
		return new Point(horizontalSpacing, verticalSpacing);
	}

	public CellLayout setMargins(int marginWidth, int marginHeight) {
		this.marginWidth = marginWidth;
		this.marginHeight = marginHeight;
		return this;
	}

	public CellLayout setMargins(Point newMargins) {
		marginWidth = newMargins.x;
		marginHeight = newMargins.y;
		return this;
	}

	public Point getMargins() {
		return new Point(marginWidth, marginHeight);
	}

	public CellLayout setDefaultColumn(Row info) {
		defaultColSettings = info;
		return this;
	}

	public CellLayout setColumn(int colNum, Row info) {
		while (cols.size() <= colNum) {
			cols.add(null);
		}

		cols.set(colNum, info);

		return this;
	}

	public CellLayout setDefaultRow(Row info) {
		defaultRowSettings = info;

		return this;
	}

	public CellLayout setRow(int rowNum, Row info) {
		while (rows.size() <= rowNum) {
			rows.add(null);
		}

		rows.set(rowNum, info);

		return this;
	}

	private Row getRow(int rowNum, boolean isHorizontal) {
		if (isHorizontal) {
			if (rowNum >= rows.size()) {
				return defaultRowSettings;
			}

			Row result = (Row) rows.get(rowNum);

			if (result == null) {
				result = defaultRowSettings;
			}

			return result;
