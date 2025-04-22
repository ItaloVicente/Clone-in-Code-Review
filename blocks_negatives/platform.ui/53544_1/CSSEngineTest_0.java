		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				return new TestElement(element.getClass().getSimpleName(),
						engine);
			}
		});
