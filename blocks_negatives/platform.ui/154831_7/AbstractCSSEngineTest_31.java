		objectUnderTest.setElementProvider(new IElementProvider() {

			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Objects.requireNonNull(element);
				if (element instanceof Element) {
					return (Element) element;
				}
				return null;
