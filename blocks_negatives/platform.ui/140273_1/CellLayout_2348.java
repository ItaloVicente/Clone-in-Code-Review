    /**
     * Object used to compute the height of rows whose properties have not been
     * explicitly set.
     */
    private Row defaultRowSettings = new Row(false);

    /**
     * Object used to compute the width of columns whose properties have not been
     * explicitly set.
     */
    private Row defaultColSettings = new Row(true);

    /**
     * horizontalSpacing specifies the number of pixels between the right
     * edge of one cell and the left edge of its neighbouring cell to
     * the right.
     *
     * The default value is 5.
     */
    int horizontalSpacing = 5;

    /**
     * verticalSpacing specifies the number of pixels between the bottom
     * edge of one cell and the top edge of its neighbouring cell underneath.
     *
     * The default value is 5.
     */
    int verticalSpacing = 5;

    /**
     * marginWidth specifies the number of pixels of horizontal margin
     * that will be placed along the left and right edges of the layout.
     *
     * The default value is 0.
     */
    public int marginWidth = 5;

    /**
     * marginHeight specifies the number of pixels of vertical margin
     * that will be placed along the top and bottom edges of the layout.
     *
     * The default value is 0.
     */
    public int marginHeight = 5;

    /**
     * Number of columns in this layout, or 0 indicating that the whole layout
     * should be on a single row.
     */
    private int numCols;

    /**
     * List of IColumnInfo. The nth object is used to compute the width of the
     * nth column, or null indicating that the default column should be used.
     */
    private List cols;

    /**
     * List of RowInfo. The nth object is used to compute the height of the
     * nth row, or null indicating that the default row should be used.
     */
    private List rows = new ArrayList(16);

    private GridInfo gridInfo = new GridInfo();

    private int[] cachedRowMin = null;

    private int[] cachedColMin = null;

    public static int cacheMisses;

    public static int cacheHits;

    private LayoutCache cache = new LayoutCache();


    /**
     * Creates the layout
     *
     * @param numCols the number of columns in this layout,
     * or 0 indicating that the whole layout should be on one row.
     */
    public CellLayout(int numCols) {
        super();
        this.numCols = numCols;
        cols = new ArrayList(numCols == 0 ? 3 : numCols);
    }

    /**
     * Sets the amount empty space between cells
     *
     * @param newSpacing a point (x,y) corresponding to the number of pixels of
     * empty space between adjacent columns and rows respectively
     */
    public CellLayout setSpacing(int horizontalSpacing, int verticalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;

        return this;
    }

    /**
     * Sets the amount empty space between cells
     *
     * @param newSpacing a point (x,y) corresponding to the number of pixels of
     * empty space between adjacent columns and rows respectively
     */
    public CellLayout setSpacing(Point newSpacing) {
        horizontalSpacing = newSpacing.x;
        verticalSpacing = newSpacing.y;
        return this;
    }

    /**
     * Returns the amount of empty space between adjacent cells
     *
     * @return a point (x,y) corresponding to the number of pixels of empty
     * space between adjacent columns and rows respectively
     */
    public Point getSpacing() {
        return new Point(horizontalSpacing, verticalSpacing);
    }

    /**
     * Sets the size of the margin around the outside of the layout.
     *
     * @param marginWidth the size of the margin around the top and
     * bottom of the layout
     * @param marginHeight the size of the margin on the left and right
     * of the layout.
     */
    public CellLayout setMargins(int marginWidth, int marginHeight) {
        this.marginWidth = marginWidth;
        this.marginHeight = marginHeight;
        return this;
    }

    /**
     * Sets the size of the margin around the outside of the layout.
     *
     * @param newMargins point indicating the size of the horizontal and vertical
     * margins, in pixels.
     */
    public CellLayout setMargins(Point newMargins) {
        marginWidth = newMargins.x;
        marginHeight = newMargins.y;
        return this;
    }

    /**
     * Returns the size of the margins around the outside of the layout.
     *
     * @return the size of the outer margins, in pixels.
     */
    public Point getMargins() {
        return new Point(marginWidth, marginHeight);
    }

    /**
     * Sets the default column settings. All columns will use these settings unless
     * they have been explicitly assigned custom settings by setColumn.
     *
     * @param info the properties of all default columns
     * @see setColumn
     */
    public CellLayout setDefaultColumn(Row info) {
        defaultColSettings = info;
        return this;
    }

    /**
     * Sets the column info for the given column number (the leftmost column is column 0).
     * This replaces any existing info for the column. Note that more than one column
     * are allowed to share the same IColumnInfo instance if they have identical properties.
     *
     * @param colNum the column number to modify
     * @param info the properties of the column, or null if this column should use the
     * default properties
     */
    public CellLayout setColumn(int colNum, Row info) {
        while (cols.size() <= colNum) {
            cols.add(null);
        }

        cols.set(colNum, info);

        return this;
    }

    /**
     * Sets the default row settings for this layout. Unless this is overridden
     * for an individual row, all rows will use the default settings.
     *
     * @param info the row info object that should be used to set the size
     * of rows, by default.
     */
    public CellLayout setDefaultRow(Row info) {
        defaultRowSettings = info;

        return this;
    }

    /**
     * Sets the row info for the given rows. The topmost row is row 0. Multiple
     * rows are allowed to share the same RowInfo instance.
     *
     * @param rowNum the row number to set
     * @param info the row info that will control the sizing of the given row,
     * or null if the row should use the default settings for this layout.
     */
    public CellLayout setRow(int rowNum, Row info) {
        while (rows.size() <= rowNum) {
            rows.add(null);
        }

        rows.set(rowNum, info);

        return this;
    }

    /**
     * Returns the row info that controls the size of the given row. Will return
     * the default row settings for this layout if no custom row info has been
     * assigned to the row.
     *
     * @param rowNum
     * @return
     */
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
