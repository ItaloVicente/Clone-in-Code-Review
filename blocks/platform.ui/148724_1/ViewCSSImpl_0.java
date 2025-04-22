		if (pseudoElt == null) {
			return cssCacheWithoutPseudo.computeIfAbsent(elt, e -> getComputedStyle(getCombinedRules(), e, pseudoElt));
		}

		Map<String, CSSStyleDeclaration> pseudoCssMap = cssCacheWithPseudo.computeIfAbsent(elt, e -> new HashMap<>());
		return pseudoCssMap.computeIfAbsent(pseudoElt, e -> getComputedStyle(getCombinedRules(), elt, pseudoElt));
