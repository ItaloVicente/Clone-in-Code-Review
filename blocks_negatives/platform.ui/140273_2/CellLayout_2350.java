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

    /**
     * Computes one dimension of the preferred size of the layout.
     *
     * @param hint contains the result if already known, or SWT.DEFAULT if it needs to be computed
     * @param constraints contains constraints along the other dimension, or SWT.DEFAULT if none. For
     * example, if we are computing the preferred row sizes, this would be an array of known column sizes.
     * @param computingRows if true, this method returns the height (pixels). Otherwise, it returns the
     * width (pixels).
     */
    int preferredSize(int[] constraints, boolean computingRows) {
        int[] fixedSizes = computeMinSizes(constraints, computingRows);

        return sumOfSizes(fixedSizes)
                + getDynamicSize(constraints, fixedSizes, computingRows);
    }

    /**
     * Computes the sum of all integers in the given array. If any of the entries are SWT.DEFAULT,
     * the result is SWT.DEFAULT.
     */
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

    /**
     * Returns the preferred dynamic width of the layout
     *
     * @param constraints
     * @param fixedSizes
     * @param computingRows
     * @return
     */
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

    /**
     * Computes one dimension of a control's size
     *
     * @param control the index of the control being computed
     * @param constraint the other dimension of the control's size, or SWT.DEFAULT if unknown
     * @param computingHeight if true, this method returns a height. Else it returns a width
     * @return the preferred height or width of the control, in pixels
     */
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

    /**
     * Returns the relative amount that a control starting on the given row and spanning
     * the given length will contribute
     *
     * @param start
     * @param length
     * @param computingRows
     * @return
     */
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

                        for (int control : rowControls) {
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

    /**
     * Returns the height constraints that should be used when computing column widths. Requires initGrid
     * to have been called first.
     *
     * @param result Will contain the height constraint for row i in the ith position of the array,
     * or SWT.DEFAULT if there is no constraint on that row.
     */
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

    /**
     * Computes the total greediness of all rows
     *
     * @return the total greediness of all rows
     */
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

    /**
     * Returns the total space that will be required for margins and spacing between and
     * around cells. initGrid(...) must have been called first.
     *
     * @return
     */
    private Point totalEmptySpace() {
        int numRows = gridInfo.getRows();

        return new Point((2 * marginWidth)
                + ((gridInfo.getCols() - 1) * horizontalSpacing),
                (2 * marginHeight) + ((numRows - 1) * verticalSpacing));
    }

    /**
     * Returns the absolute positions of each row, given the start position, row sizes,
     * and row spacing
     *
     * @param startPos position of the initial row
     * @param sizes array of row sizes (pixels)
     * @param spacing space between each row (pixels)
     * @return array of row positions. The result size is sizes.length + 1. The last entry is
     * the position of the end of the layout.
     */
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
