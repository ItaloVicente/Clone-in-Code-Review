	implements DescendantSelector,
				ExtendedSelector {

	protected Selector ancestorSelector;

	protected SimpleSelector simpleSelector;

	protected AbstractDescendantSelector(Selector ancestor,
										 SimpleSelector simple) {
		ancestorSelector = ancestor;
		simpleSelector = simple;
	}

	@Override
