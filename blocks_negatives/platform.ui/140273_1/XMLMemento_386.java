     * A simple XML writer.  Using this instead of the javax.xml.transform classes allows
     * compilation against JCL Foundation (bug 80053).
     */
    private static final class DOMWriter extends PrintWriter {

    	/* constants */

    	/**
    	 * Creates a new DOM writer on the given output writer.
    	 *
    	 * @param output the output writer
    	 */
    	public DOMWriter(Writer output) {
    		super(output);
    		println(XML_VERSION);
    	}

    	/**
    	 * Prints the given element.
    	 *
    	 * @param element the element to print
    	 */
        public void print(Element element) {
        	boolean hasChildren = element.hasChildNodes();
        	startTag(element, hasChildren);
        	if (hasChildren) {
	        	boolean prevWasText = false;
	        	NodeList children = element.getChildNodes();
	    		for (int i = 0; i < children.getLength(); i++) {
	    			Node node = children.item(i);
	    			if (node instanceof Element) {
	    				if (!prevWasText) {
	    					println();
	    				}
	    				print((Element) children.item(i));
	    				prevWasText = false;
	    			}
	    			else if (node instanceof Text) {
	    				print(getEscaped(node.getNodeValue()));
	    				prevWasText = true;
	    			}
	    		}
	    		if (!prevWasText) {
	    			println();
	    		}
	    		endTag(element);
        	}
    	}

    	private void startTag(Element element, boolean hasChildren) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(element.getTagName());
    		NamedNodeMap attributes = element.getAttributes();
   			for (int i = 0;  i < attributes.getLength(); i++) {
   				Attr attribute = (Attr)attributes.item(i);
