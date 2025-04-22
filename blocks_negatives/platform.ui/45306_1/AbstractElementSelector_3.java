public abstract class AbstractElementSelector
    implements ElementSelector,
               ExtendedSelector {

    /**
     * The namespace URI.
     */
    protected String namespaceURI;

    /**
     * The local name.
     */
    protected String localName;

    /**
     * Creates a new ElementSelector object.
     */
    protected AbstractElementSelector(String uri, String name) {
        namespaceURI = uri;
        localName    = name;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    @Override
