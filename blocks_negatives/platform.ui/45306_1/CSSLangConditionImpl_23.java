public class CSSLangConditionImpl
    implements LangCondition,
               ExtendedCondition {
    /**
     * The language.
     */
    protected String lang;

    /**
     * The language with a hyphen suffixed.
     */
    protected String langHyphen;

    /**
     * Creates a new LangCondition object.
     */
    public CSSLangConditionImpl(String lang) {
        this.lang = lang.toLowerCase();
        this.langHyphen = lang + '-';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    @Override
