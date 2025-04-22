public class CSSConditionalSelectorImpl
    implements ConditionalSelector,
               ExtendedSelector {

    /**
     * The simple selector.
     */
    protected SimpleSelector simpleSelector;

    /**
     * The condition.
     */
    protected Condition condition;

    /**
     * Creates a new ConditionalSelector object.
     */
    public CSSConditionalSelectorImpl(SimpleSelector s, Condition c) {
        simpleSelector = s;
        condition      = c;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    @Override
