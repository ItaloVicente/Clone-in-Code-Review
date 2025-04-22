		preservingSelection(new Runnable() {
			@Override
			public void run() {
				if (internalIsInputOrEmptyPath(elementOrTreePath)) {
					if (hasChildren) {
						virtualLazyUpdateChildCount(getTree(),
								getChildren(getTree()).length);
					} else {
						setChildCount(elementOrTreePath, 0);
					}
					return;
