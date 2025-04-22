				parent.pack();
				parent.requestLayout();

				if (parent instanceof CoolBar) {
					CoolBar cb = (CoolBar) layoutBar.getParent();
					CoolItem[] items = cb.getItems();
					for (CoolItem item : items) {
						if (item.getControl() == layoutBar) {
							Point curSize = item.getSize();
							item.setSize(curSize.x + (afterPack.x - beforePack.x),
									curSize.y + (afterPack.y - beforePack.y));
							return;
						}
