
			private boolean needsToBeRedrawn(Widget widget, Widget item) {
				if (!(widget instanceof Tree)) {
					return false;
				}

				Tree tree = (Tree) widget;

				if (tree.getSelectionCount() == 1
						&& tree.getSelection()[0] == item) {
					return true;
				}

				if (tree.getSelectionCount() > 1) {
					return true;
				}

				if (tree.getSelectionCount() == 0) {
					return true;
				}

				return false;
			}
