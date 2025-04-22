		listener = event -> {
			@SuppressWarnings("unchecked")
			E element = (E) event.getElement();
			if (event.getChecked()) {
				if (wrappedSet.add(element))
					fireSetChange(Diffs.createSetDiff(Collections.singleton(element), Collections.emptySet()));
			} else {
				if (wrappedSet.remove(element))
					fireSetChange(Diffs.createSetDiff(Collections.emptySet(), Collections.singleton(element)));
