		while (!(curParent instanceof MPerspective) && !(curParent instanceof MWindow)) {
			if (curParent == null) {
				current = findPlaceholderFor(window, current);
				if (current == null) {
				}

				curParent = current.getParent();
				if (curParent == null) {
				}
			}
			current = curParent;
			curParent = current.getParent();
		}
