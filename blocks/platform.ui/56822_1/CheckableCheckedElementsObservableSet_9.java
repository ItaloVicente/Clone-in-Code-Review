		listener = event -> {
			Object element = event.getElement();
			if (event.getChecked()) {
				if (wrappedSet.add(element))
					fireSetChange(Diffs.createSetDiff(Collections
							.singleton(element), Collections.EMPTY_SET));
			} else {
				if (wrappedSet.remove(element))
					fireSetChange(Diffs.createSetDiff(
							Collections.EMPTY_SET, Collections
									.singleton(element)));
