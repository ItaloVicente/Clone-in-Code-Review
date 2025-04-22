		if (style != null) {
			applyStyleDeclaration(elt, style, null);
		}
		try {
			applyInlineStyle(elt, false);
		} catch (Exception e) {
			handleExceptions(e);
		}
