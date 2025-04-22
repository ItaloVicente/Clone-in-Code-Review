    /**
     * Default size for controls with varying contents
     */
    private static final Point defaultSize = new Point(150, 150);

    /**
     * Default wrapping size for wrapped labels
     */
    private static final int wrapSize = 350;

    private static final GridDataFactory nonWrappingLabelData = GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(false, false);

    private static boolean hasStyle(Control c, int style) {
        return (c.getStyle() & style) != 0;
    }

    /**
     * Generates a GridLayout for the given composite by examining its child
     * controls and attaching layout data to any immediate children that do not
     * already have layout data.
     *
     * @param toGenerate
     *            composite to generate a layout for
     */
    public static void generateLayout(Composite toGenerate) {
        Control[] children = toGenerate.getChildren();

        for (Control control : children) {
            if (control.getLayoutData() != null) {
                continue;
            }

            applyLayoutDataTo(control);
        }
    }

    private static void applyLayoutDataTo(Control control) {
    	defaultsFor(control).applyTo(control);
    }

    /**
     * Creates default factory for this control types:
     * <ul>
     * 	<li>{@link Button} with {@link SWT#CHECK}</li>
     * 	<li>{@link Button}</li>
     * 	<li>{@link Composite}</li>
     * </ul>
     * @param control the control the factory is search for
     * @return a default factory for the control
     */
    public static GridDataFactory defaultsFor(Control control) {
        if (control instanceof Button) {
            Button button = (Button) control;

            if (hasStyle(button, SWT.CHECK)) {
                return nonWrappingLabelData.copy();
            }
            return GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).hint(Geometry.max(button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true), LayoutConstants.getMinButtonSize()));
        }

        if (control instanceof Scrollable) {
            Scrollable scrollable = (Scrollable) control;

            if (scrollable instanceof Composite) {
                Composite composite = (Composite) control;

                Layout theLayout = composite.getLayout();
                if (theLayout instanceof GridLayout) {
                    boolean growsHorizontally = false;
                    boolean growsVertically = false;

                    Control[] children = composite.getChildren();
                    for (Control child : children) {
                        GridData data = (GridData) child.getLayoutData();

                        if (data != null) {
                            if (data.grabExcessHorizontalSpace) {
                                growsHorizontally = true;
                            }
                            if (data.grabExcessVerticalSpace) {
                                growsVertically = true;
                            }
                        }
                    }

                    return GridDataFactory.fillDefaults().grab(growsHorizontally, growsVertically);
                }
            }
        }

        boolean wrapping = hasStyle(control, SWT.WRAP);
