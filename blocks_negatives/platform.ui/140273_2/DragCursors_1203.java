    public static final int INVALID = 0;

    public static final int LEFT = 1;

    public static final int RIGHT = 2;

    public static final int TOP = 3;

    public static final int BOTTOM = 4;

    public static final int CENTER = 5;

    public static final int OFFSCREEN = 6;

    public static final int FASTVIEW = 7;

    private static final Cursor cursors[] = new Cursor[8];

    public static int positionToDragCursor(int swtPositionConstant) {
        switch (swtPositionConstant) {
        case SWT.LEFT:
            return LEFT;
        case SWT.RIGHT:
            return RIGHT;
        case SWT.TOP:
            return TOP;
        case SWT.BOTTOM:
            return BOTTOM;
        case SWT.CENTER:
            return CENTER;
        }

        return INVALID;
    }

    /**
     * Converts a drag cursor (LEFT, RIGHT, TOP, BOTTOM, CENTER) into an SWT constant
     * (SWT.LEFT, SWT.RIGHT, SWT.TOP, SWT.BOTTOM, SWT.CENTER)
     *
     * @param dragCursorId
     * @return an SWT.* constant
     */
    public static int dragCursorToSwtConstant(int dragCursorId) {
        switch (dragCursorId) {
        case LEFT:
            return SWT.LEFT;
        case RIGHT:
            return SWT.RIGHT;
        case TOP:
            return SWT.TOP;
        case BOTTOM:
            return SWT.BOTTOM;
        case CENTER:
            return SWT.CENTER;
        }

        return SWT.DEFAULT;
    }
