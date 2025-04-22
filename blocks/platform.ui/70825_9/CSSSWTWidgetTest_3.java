	private final class WidgetElementWithSupplierReturningNull extends WidgetElement {
		private WidgetElementWithSupplierReturningNull(Widget widget, CSSEngine engine) {
			super(widget, engine);
		}

		@Override
		protected Supplier<String> internalGetAttribute(String attr) {
			return () -> null;
		}
	}

	private final class SWTHTMLElementWithAttributeTypeNull extends SWTHTMLElement {
		private SWTHTMLElementWithAttributeTypeNull(Widget widget, CSSEngine engine) {
			super(widget, engine);
			attributeType = null;
		}
	}

	private final class WidgetElementWithSwtStylesNull extends WidgetElement {

		private WidgetElementWithSwtStylesNull(Widget widget, CSSEngine engine) {
			super(widget, engine);
			swtStyles = null;
		}
	}

