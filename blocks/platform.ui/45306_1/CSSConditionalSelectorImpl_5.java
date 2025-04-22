public class CSSConditionalSelectorImpl implements ConditionalSelector, ExtendedSelector {

	protected SimpleSelector simpleSelector;

	protected Condition condition;

	public CSSConditionalSelectorImpl(SimpleSelector s, Condition c) {
		simpleSelector = s;
		condition      = c;
	}

	@Override
