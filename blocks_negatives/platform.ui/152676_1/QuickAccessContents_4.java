					if (isPreviousPickProvider) {
						prevPick = sortedElements.length;
						Stream.of(sortedElements).map(QuickAccessElement::getId).forEach(prevPickIds::add);
					}
					if (!filter.isEmpty() && sortedElements.length > 0) {
						sortedElements = putPrefixMatchFirst(sortedElements, filter);
