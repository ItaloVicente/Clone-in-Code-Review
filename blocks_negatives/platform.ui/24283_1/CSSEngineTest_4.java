	public abstract static class BaseElement extends ElementAdapter {
		public BaseElement(Object object, CSSEngine engine) {
			super(object, engine);
		}

		public Node getParentNode() {
			return null;
		}

		public NodeList getChildNodes() {
			return null;
		}

		public String getNamespaceURI() {
			return null;
		}

		public String getCSSId() {
			return null;
		}

		public String getCSSClass() {
			return null;
		}

		public String getCSSStyle() {
			return null;
		}

		public String getLocalName() {
			return null;
		}

		public String getAttribute(String arg0) {
			return null;
		}
	}
	
	public static class CollectionElement extends BaseElement {
		public CollectionElement(Collection control, CSSEngine engine) {
			super(control, engine);
		}
	}

	public static class SetElement extends BaseElement {
		public SetElement(Set object, CSSEngine engine) {
			super(object, engine);
		}
	}

	public void testBug363053() {
		TestCSSEngine engine = new TestCSSEngine();
		engine.setWidgetProvider(AbstractCollection.class,
				new IElementProvider() {
			public Element getElement(Object element, CSSEngine engine) {
				return new CollectionElement((Collection) element, engine);
			}
		});
		engine.setWidgetProvider(AbstractSet.class, new IElementProvider() {
			public Element getElement(Object element, CSSEngine engine) {
				return new SetElement((Set) element, engine);
			}
		});

		Element element = engine.getElement(new ArrayList());
		assertTrue(element instanceof CollectionElement);

		element = engine.getElement(new HashSet());
		assertTrue(element instanceof SetElement);
	}

