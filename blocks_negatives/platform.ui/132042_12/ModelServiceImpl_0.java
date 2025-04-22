		if (classMatch && matcher.select(searchRoot)) {
			if (!elements.contains(searchRoot)) {
				@SuppressWarnings("unchecked")
				T element = (T) searchRoot;
				elements.add(element);
			}
