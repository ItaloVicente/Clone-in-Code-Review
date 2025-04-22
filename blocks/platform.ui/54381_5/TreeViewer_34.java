		preservingSelection(() -> {
			if (internalIsInputOrEmptyPath(elementOrTreePath)) {
				if (hasChildren) {
					virtualLazyUpdateChildCount(getTree(),
							getChildren(getTree()).length);
				} else {
					setChildCount(elementOrTreePath, 0);
