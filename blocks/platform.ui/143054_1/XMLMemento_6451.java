	private static final class DOMWriter extends PrintWriter {

		private static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$

		public DOMWriter(Writer output) {
			super(output);
			println(XML_VERSION);
		}

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
					} else if (node instanceof Text) {
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
			sb.append("<"); //$NON-NLS-1$
			sb.append(element.getTagName());
			NamedNodeMap attributes = element.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr) attributes.item(i);
