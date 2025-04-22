    implements DescendantSelector,
               ExtendedSelector {

    /**
     * The ancestor selector.
     */
    protected Selector ancestorSelector;

    /**
     * The simple selector.
     */
    protected SimpleSelector simpleSelector;

    /**
     * Creates a new DescendantSelector object.
     */
    protected AbstractDescendantSelector(Selector ancestor,
                                         SimpleSelector simple) {
        ancestorSelector = ancestor;
        simpleSelector = simple;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    @Override
