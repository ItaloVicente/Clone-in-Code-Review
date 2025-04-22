			if (cssCacheWithoutPseudo.get(elt) != null) {
				System.out.println("Cache hit for !!" + elt + " " + cssCacheWithoutPseudo.get(elt));
			}
			CSSStyleDeclaration result = cssCacheWithoutPseudo.computeIfAbsent(elt,
					e -> getComputedStyle(getCombinedRules(), e, pseudoElt));
			return result;
		}
		if (cssCacheWithPseudo.get(elt) != null) {
			System.out.println("Cache hit for !!" + elt + " " + pseudoElt);
