		@Override
		public CSSStyleDeclaration getStyle(Object widget) {
			for (CSSEngine engine : cssEngines) {
				CSSElementContext context = engine.getCSSElementContext(widget);
				if (context != null) {
					Element e = context.getElement();
					if (e != null) {
						return engine.getViewCSS().getComputedStyle(e, null);
					}
