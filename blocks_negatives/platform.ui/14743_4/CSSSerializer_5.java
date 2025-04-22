		for (Iterator iterator = selectors.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String selectorName = (String) entry.getKey();
			CSSStyleDeclaration styleDeclaration = (CSSStyleDeclaration) entry
					.getValue();
