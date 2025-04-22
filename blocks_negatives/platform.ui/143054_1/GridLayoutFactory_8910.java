    /**
     * Applies this layout to the given composite, and attaches default GridData
     * to all immediate children that don't have one. The layout is generated using
     * heuristics based on the widget types. In most cases, it will create exactly the same
     * layout that would have been hardcoded by the programmer. In any situation
     * where it does not produce the desired layout, the GridData for any child can be
     * overridden by attaching the layout data before calling this method. In these cases,
     * the special-case layout data can be hardcoded and the algorithm can supply defaults
     * to the rest.
     *
     * <p>
     * This must be called <b>AFTER</b> all of the child controls have been created and their
     * layouts attached. This method will attach a layout to the given composite. If any new
     * children are created after calling this method, their GridData must be created manually.
     * The algorithm does not recurse into child composites. To generate all the layouts in
     * a widget hierarchy, the method must be called bottom-up for each Composite.
     * </p>
     *
     * <p>
     * All controls are made to span a single cell. The algorithm tries to classify controls into one
     * of the following categories:
     * </p>
     *
     * <ul>
     * <li>Pushbuttons: Set to a constant size large enough to fit their text and no smaller
     * than the default button size.</li>
     * <li>Wrapping with text (labels, read-only text boxes, etc.): override the preferred horizontal
     *     size with the default wrapping point, fill horizontally, grab horizontal space, keep the
     *     preferred vertical size</li>
     * <li>Wrapping without text (toolbars, coolbars, etc.): fill align, don't grab, use the preferred size</li>
     * <li>Horizontally scrolling controls (anything with horizontal scrollbars or where the user edits
     *     text and can cursor through it from left-to-right): override the preferred horizontal size with
     *     a constant, grab horizontal, fill horizontal.</li>
     * <li>Vertically scrolling controls (anything with vertical scrollbars or where the user edits
     *     text and can cursor through it up and down): override the preferred vertical size with a constant,
     *     grab vertical, fill vertical</li>
     * <li>Nested layouts: fill align both directions, grab along any dimension if the layout would
     *     be able to expand along that dimension.</li>
     * <li>Non-wrapping non-scrollable read-only text: fill horizontally, center vertically, default size, don't grab </li>
     * <li>Non-wrapping non-scrollable non-text: fill both, default size, don't grab</li>
     * </ul>
     *
     * @param c composite whose layout will be generated
     */
    public void generateLayout(Composite c) {
        applyTo(c);
        LayoutGenerator.generateLayout(c);
    }
