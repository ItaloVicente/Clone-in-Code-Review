			/*
			 * Compute new Style to apply.
			 */
			CSSStyleDeclaration style = viewCSS.getComputedStyle(elt, null);
			if (computeDefaultStyle) {
				if (applyStylesToChildNodes) {
					this.computeDefaultStyle = computeDefaultStyle;
				}
				/*
				 * Apply default style.
				 */
				applyDefaultStyleDeclaration(element, false, style, null);
