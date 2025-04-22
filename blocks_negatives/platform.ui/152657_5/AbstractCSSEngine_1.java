			String[] pseudoInstances = getStaticPseudoInstances(elt);
			if (pseudoInstances != null && pseudoInstances.length > 0) {
				for (String pseudoInstance : pseudoInstances) {
					CSSStyleDeclaration styleWithPseudoInstance = viewCSS.getComputedStyle(elt, pseudoInstance);
					if (computeDefaultStyle) {
						/*
						 * Apply default style for the current pseudo instance.
						 */
						applyDefaultStyleDeclaration(element, false, styleWithPseudoInstance, pseudoInstance);
					}
