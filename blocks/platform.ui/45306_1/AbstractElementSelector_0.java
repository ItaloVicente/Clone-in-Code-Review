public abstract class AbstractElementSelector implements ElementSelector, ExtendedSelector {

	protected String namespaceURI;

	protected String localName;

	protected AbstractElementSelector(String uri, String name) {
		namespaceURI = uri;
		localName    = name;
	}

	@Override
