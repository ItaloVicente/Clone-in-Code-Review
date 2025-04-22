					if (styleWithPseudoInstance != null) {
						CSSRule parentRule = styleWithPseudoInstance.getParentRule();
						if (parentRule instanceof ExtendedCSSRule) {
							applyConditionalPseudoStyle((ExtendedCSSRule) parentRule, pseudoInstance, element, styleWithPseudoInstance);
						} else {
							applyStyleDeclaration(elt, styleWithPseudoInstance, pseudoInstance);
						}
