			if (parent == null) {
				MPlaceholder placeholder = container.getCurSharedRef();
				if (placeholder != null) {
					return placeholder.getParent().getWidget();
				}
			} else {
				return parent.getWidget();
