			CSSElementContext context = elementsContext.remove(widget);
			if (context != null) {
				Element element = context.getElement();
				if (element instanceof ElementAdapter) {
					((ElementAdapter) element).dispose();
				}
			}
