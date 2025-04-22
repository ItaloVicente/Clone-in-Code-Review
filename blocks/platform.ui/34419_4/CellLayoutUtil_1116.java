
package org.eclipse.ui.internal.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class CellLayout extends Layout {

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
        } else {
            if (rowNum >= cols.size()) {
                return defaultColSettings;
            }

            Row result = (Row) cols.get(rowNum);

            if (result == null) {
                result = defaultColSettings;
            }

            return result;
        }
    }

    private void initGrid(Control[] children) {
        cache.setControls(children);
        gridInfo.initGrid(children, this);
        cachedRowMin = null;
        cachedColMin = null;
    }

    @Override
	protected Point computeSize(Composite composite, int wHint, int hHint,
            boolean flushCache) {
        Control[] children = composite.getChildren();
        initGrid(children);

        if (flushCache) {
            cache.flush();
        }

        Point emptySpace = totalEmptySpace();

        int[] heightConstraints = computeConstraints(true);

        int width;
        if (wHint == SWT.DEFAULT) {
            width = preferredSize(heightConstraints, false);
        } else {
            width = wHint - emptySpace.x;
        }

        int height = hHint;
        if (hHint == SWT.DEFAULT) {
            height = preferredSize(
                    computeSizes(heightConstraints, width, false), true);
        } else {
            height = hHint - emptySpace.y;
        }

        Point preferredSize = new Point(width + emptySpace.x, height
                + emptySpace.y);


        Point minimumSize = CellLayoutUtil.computeMinimumSize(composite);

        boolean wider = (preferredSize.x >= minimumSize.x);
        boolean taller = (preferredSize.y >= minimumSize.y);

        if (wider) {
            if (taller) {
                return preferredSize;
            } else {
                return computeSize(composite, wHint, minimumSize.y, false);
            }
        } else {
            if (taller) {
                return computeSize(composite, minimumSize.x, hHint, false);
            } else {
                return minimumSize;
            }
        }
    }

    int[] computeSizes(int[] constraints, int availableSpace,
            boolean computingRows) {
        int[] result = computeMinSizes(constraints, computingRows);

        int totalFixed = sumOfSizes(result);
        int denominator = getResizeDenominator(computingRows);
        int numRows = gridInfo.getNumRows(computingRows);

        if (totalFixed < availableSpace) {
            int remaining = availableSpace - totalFixed;

            for (int idx = 0; idx < numRows && denominator > 0; idx++) {
                Row row = getRow(idx, computingRows);

                if (row.grows) {
                    int greed = row.size;
                    int amount = remaining * greed / denominator;

                    result[idx] += amount;
                    remaining -= amount;
                    denominator -= greed;
                }
            }
        }

        return result;
    }

    int preferredSize(int[] constraints, boolean computingRows) {
        int[] fixedSizes = computeMinSizes(constraints, computingRows);

        return sumOfSizes(fixedSizes)
                + getDynamicSize(constraints, fixedSizes, computingRows);
    }

    static int sumOfSizes(int[] input) {
        return sumOfSizes(input, 0, input.length);
    }

    static int sumOfSizes(int[] input, int start, int length) {
        int sum = 0;
        for (int idx = start; idx < start + length; idx++) {
            int next = input[idx];

            if (next == SWT.DEFAULT) {
                return SWT.DEFAULT;
            }

            sum += next;
        }

        return sum;
    }

    int getDynamicSize(int[] constraints, int[] fixedSizes,
            boolean computingRows) {
        int result = 0;
        int numerator = getResizeDenominator(computingRows);

        if (numerator == 0) {
            return 0;
        }

        int rowSpacing = computingRows ? verticalSpacing : horizontalSpacing;
        int colSpacing = computingRows ? horizontalSpacing : verticalSpacing;

        int numControls = gridInfo.controls.length;
        for (int idx = 0; idx < numControls; idx++) {
            int controlRowStart = gridInfo.getStartPos(idx, computingRows);
            int controlRowSpan = getSpan(idx, computingRows);
            int controlColStart = gridInfo.getStartPos(idx, !computingRows);
            int controlColSpan = getSpan(idx, !computingRows);

            int denominator = getGrowthRatio(controlRowStart, controlRowSpan,
                    computingRows);

            if (denominator > 0) {

                int widthHint = sumOfSizes(constraints, controlColStart,
                        controlColSpan);
                if (widthHint != SWT.DEFAULT) {
                    widthHint += colSpacing * (controlColSpan - 1);
                }

                int controlSize = computeControlSize(idx, widthHint,
                        computingRows);

                controlSize -= sumOfSizes(fixedSizes, controlRowStart,
                        controlRowSpan);

                controlSize -= (rowSpacing * (controlRowSpan - 1));

                result = Math
                        .max(result, controlSize * numerator / denominator);
            }
        }

        return result;
    }

    int computeControlSize(int control, int constraint, boolean computingHeight) {
        CellData data = gridInfo.getCellData(control);

        if (constraint == SWT.DEFAULT) {
            Point result = data.computeSize(cache.getCache(control),
                    SWT.DEFAULT, SWT.DEFAULT);

            if (computingHeight) {
                return result.y;
            }
            return result.x;
        }

        if (computingHeight) {
            return data.computeSize(cache.getCache(control), constraint,
                    SWT.DEFAULT).y;
        }

        return data.computeSize(cache.getCache(control), SWT.DEFAULT,
                constraint).x;
    }

    int getGrowthRatio(int start, int length, boolean computingRows) {
        boolean willGrow = false;
        int sum = 0;

        int end = start + length;
        for (int idx = start; idx < end; idx++) {
            Row row = getRow(idx, computingRows);

            if (row.largerThanChildren && row.grows) {
                willGrow = true;
            }

            sum += row.size;
        }

        if (!willGrow) {
            return 0;
        }

        return sum;
    }

    int[] computeMinSizes(int[] constraints, boolean computingRows) {
        int[] result = computingRows ? cachedRowMin : cachedColMin;

        if (result == null) {
            int columnSpacing;
            int rowSpacing;

            if (computingRows) {
                columnSpacing = horizontalSpacing;
                rowSpacing = verticalSpacing;
            } else {
                columnSpacing = verticalSpacing;
                rowSpacing = horizontalSpacing;
            }

            int rowCount = gridInfo.getNumRows(computingRows);
            result = new int[rowCount];
            int colCount = gridInfo.getNumRows(!computingRows);
            int[] rowControls = new int[colCount];

            int lastGrowingRow = -1;

            for (int idx = 0; idx < rowCount; idx++) {
                Row row = getRow(idx, computingRows);

                if (row.grows) {
                    lastGrowingRow = idx;
                    result[idx] = 0;
                } else {
                    result[idx] = row.size;

                    if (row.largerThanChildren) {
                        gridInfo.getRow(rowControls, idx, computingRows);

                        for (int colIdx = 0; colIdx < rowControls.length; colIdx++) {
                            int control = rowControls[colIdx];

                            if (control != -1) {
                                int controlStart = gridInfo.getStartPos(
                                        control, computingRows);
                                int controlSpan = getSpan(control,
                                        computingRows);

                                if (controlStart + controlSpan - 1 == idx
                                        && controlStart > lastGrowingRow) {
                                    int controlColStart = gridInfo.getStartPos(
                                            control, !computingRows);
                                    int controlColSpan = getSpan(control,
                                            !computingRows);
                                    int controlRowSpan = getSpan(control,
                                            computingRows);

                                    int spannedWidth = sumOfSizes(constraints,
                                            controlColStart, controlColSpan);
                                    if (spannedWidth != SWT.DEFAULT) {
                                        spannedWidth += (columnSpacing * (controlSpan - 1));
                                    }

                                    int controlHeight = computeControlSize(
                                            control, spannedWidth,
                                            computingRows);

                                    int allocatedHeight = sumOfSizes(result,
                                            controlColStart, controlRowSpan - 1)
                                            + (rowSpacing * (controlRowSpan - 1));

                                    result[idx] = Math.max(result[idx],
                                            controlHeight - allocatedHeight);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (computingRows) {
            cachedRowMin = result;
        } else {
            cachedColMin = result;
        }

        return result;
    }

    private int[] computeConstraints(boolean horizontal) {
        int numRows = gridInfo.getNumRows(horizontal);
        int[] result = new int[numRows];

        for (int idx = 0; idx < numRows; idx++) {
            Row row = getRow(idx, horizontal);

            if (!(row.grows || row.largerThanChildren)) {
                result[idx] = row.size;
            } else {
                result[idx] = SWT.DEFAULT;
            }
        }

        return result;
    }

    private int getResizeDenominator(boolean horizontal) {
        int result = 0;
        int numRows = gridInfo.getNumRows(horizontal);

        for (int idx = 0; idx < numRows; idx++) {
            Row row = getRow(idx, horizontal);

            if (row.grows) {
                result += row.size;
            }
        }

        return result;
    }


    protected int getSpan(int controlId, boolean isRow) {
        CellData data = gridInfo.getCellData(controlId);

        if (isRow) {
            return data.verticalSpan;
        }
        return data.horizontalSpan;
    }

    private Point totalEmptySpace() {
        int numRows = gridInfo.getRows();

        return new Point((2 * marginWidth)
                + ((gridInfo.getCols() - 1) * horizontalSpacing),
                (2 * marginHeight) + ((numRows - 1) * verticalSpacing));
    }

    private static int[] computeRowPositions(int startPos, int[] sizes,
            int spacing) {
        int[] result = new int[sizes.length + 1];

        result[0] = startPos;
        for (int idx = 0; idx < sizes.length; idx++) {
            result[idx + 1] = result[idx] + sizes[idx] + spacing;
        }

        return result;
    }

    @Override
	protected void layout(Composite composite, boolean flushCache) {
        Control[] children = composite.getChildren();
        
        if (children.length == 0)
        	return;
        	
        initGrid(children);

        if (flushCache) {
            cache.flush();
        }

        Point emptySpace = totalEmptySpace();

        int availableWidth = composite.getClientArea().width - emptySpace.x;
        int availableHeight = composite.getClientArea().height - emptySpace.y;

        int[] heights = computeConstraints(true);
        int[] widths = new int[gridInfo.getCols()];

        widths = computeSizes(heights, availableWidth, false);

        heights = computeSizes(widths, availableHeight, true);

        Rectangle currentCell = new Rectangle(0, 0, 0, 0);

        int[] starty = computeRowPositions(composite.getClientArea().y
                + marginHeight, heights, verticalSpacing);
        int[] startx = computeRowPositions(composite.getClientArea().x
                + marginWidth, widths, horizontalSpacing);

        int numChildren = gridInfo.controls.length;
        for (int controlId = 0; controlId < numChildren; controlId++) {
            CellData data = gridInfo.getCellData(controlId);

            int row = gridInfo.controlRow[controlId];
            int col = gridInfo.controlCol[controlId];

            currentCell.x = startx[col];
            currentCell.width = startx[col + data.horizontalSpan]
                    - currentCell.x - horizontalSpacing;

            currentCell.y = starty[row];
            currentCell.height = starty[row + data.verticalSpan]
                    - currentCell.y - verticalSpacing;

            data.positionControl(cache.getCache(controlId), currentCell);
        }
    }

    public int getColumns() {
        return numCols;
    }

    public boolean canGrow(Composite composite, boolean horizontally) {
        initGrid(composite.getChildren());

        int numRows = gridInfo.getNumRows(horizontally);

        for (int idx = 0; idx < numRows; idx++) {
            Row row = getRow(idx, horizontally);

            if (row.grows) {
                return true;
            }
        }

        return false;

    }
}
