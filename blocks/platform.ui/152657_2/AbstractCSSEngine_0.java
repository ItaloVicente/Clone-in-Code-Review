		String[] pseudoInstances = getStaticPseudoInstances(elt);
		if (pseudoInstances != null && pseudoInstances.length > 0) {
			for (String pseudoInstance : pseudoInstances) {
				CSSStyleDeclaration styleWithPseudoInstance = viewCSS.getComputedStyle(elt, pseudoInstance);
				if (computeDefaultStyle) {
					applyDefaultStyleDeclaration(element, false, styleWithPseudoInstance, pseudoInstance);
				}

				if (styleWithPseudoInstance != null) {
					CSSRule parentRule = styleWithPseudoInstance.getParentRule();
					if (parentRule instanceof ExtendedCSSRule) {
						applyConditionalPseudoStyle((ExtendedCSSRule) parentRule, pseudoInstance, element, styleWithPseudoInstance);
					} else {
						applyStyleDeclaration(elt, styleWithPseudoInstance, pseudoInstance);
