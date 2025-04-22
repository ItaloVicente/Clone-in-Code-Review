		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element e = new TestElement("E", engine);
				e.setAttribute("a", element.toString());
				return e;
			}
