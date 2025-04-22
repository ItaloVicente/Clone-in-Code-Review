public class CSSLangConditionImpl implements LangCondition, ExtendedCondition {
	protected String lang;

	protected String langHyphen;

	public CSSLangConditionImpl(String lang) {
		this.lang = lang.toLowerCase();
		this.langHyphen = lang + '-';
	}

	@Override
