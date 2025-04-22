    public ActivateTest(String name) {
        super(name);
    }

    public abstract IWorkbenchPart getStackedPart1();
    public abstract IWorkbenchPart getStackedPart2();
    public abstract IWorkbenchPart getUnstackedPart();

    /**
     * <p>Test: Zoom a part and activate it</p>
     * <p>Expected result: Part remains zoomed</p>
     */
    public void testZoomAndActivate() {
        IWorkbenchPart stacked1 = getStackedPart1();

        zoom(stacked1);
        page.activate(stacked1);

        assertZoomed(stacked1);
        assertActive(stacked1);
    }

    /**
     * <p>Test: Zoom a view then activate another view in the same stack</p>
     * <p>Expected result: Stack remains zoomed</p>
     */
    public void testActivateSameStack() {
        IWorkbenchPart stacked1 = getStackedPart1();
        IWorkbenchPart stacked2 = getStackedPart2();

        zoom(stacked1);

        page.activate(stacked2);

        assertZoomed(stacked2);
        assertActive(stacked2);
    }

    /**
     * <p>Test: Zoom a view than activate a view in a different stack</p>
     * <p>Expected result: page unzooms</p>
     */
    public void testActivateOtherStack() {
    	System.out.println("Bogus Test: " + getName());
