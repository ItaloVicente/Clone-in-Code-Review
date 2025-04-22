    private int cols = 0;

    private int rows = 0;

    private int[] gridInfo;

    int[] controlRow;

    int[] controlCol;

    private CellData[] cellData;

    Control[] controls;

    /**
     * Initialize the grid
     *
     * @param newControls
     * @param cols
     */
    public void initGrid(Control[] newControls, CellLayout layout) {
        cols = layout.getColumns();
        controls = newControls;

        int area = 0;
        int totalWidth = 0;

        controlRow = new int[controls.length];
        controlCol = new int[controls.length];

        cellData = new CellData[controls.length];
        for (int idx = 0; idx < controls.length; idx++) {
            if (controls[idx] == null) {
                continue;
            }

            CellData next = CellLayoutUtil.getData(controls[idx]);
            cellData[idx] = next;
            area += next.horizontalSpan * next.verticalSpan;
            totalWidth += next.horizontalSpan;
        }

        if (cols == 0) {
            cols = totalWidth;
        }

        rows = area / cols;
        if (area % cols > 0) {
            rows++;
        }

        area = rows * cols;

        gridInfo = new int[area];
        for (int idx = 0; idx < area; idx++) {
            gridInfo[idx] = -1;
        }

        int infoIdx = 0;
        for (int idx = 0; idx < controls.length; idx++) {
            CellData data = cellData[idx];

            while (gridInfo[infoIdx] >= 0) {
                infoIdx++;
            }

            controlRow[idx] = infoIdx / cols;
            controlCol[idx] = infoIdx % cols;

            for (int rowIdx = 0; rowIdx < data.verticalSpan; rowIdx++) {
                for (int colIdx = 0; colIdx < data.horizontalSpan; colIdx++) {
                    gridInfo[infoIdx + rowIdx * cols + colIdx] = idx;
                }
            }

            infoIdx += data.horizontalSpan;
        }
    }

    public int getRows() {
        return rows;
    }

    public int getStartPos(int control, boolean row) {
        if (row) {
            return controlRow[control];
        }
