		BusyIndicator.showWhile(event.display, () -> {
			if (contentProviderIsLazy) {
				if (event.item.getData() != null) {
					Item[] children = getChildren(event.item);
					if (children.length == 1 && children[0].getData() == null) {
						virtualLazyUpdateChildCount(event.item, children.length);
					}
					fireTreeExpanded(new TreeExpansionEvent(this, event.item.getData()));
