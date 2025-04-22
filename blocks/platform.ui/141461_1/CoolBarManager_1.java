				ArrayList<Control> children = new ArrayList<>(items.length);
				for (CoolItem item : items) {
					if ((item.getControl() != null) && (!item.getControl().isDisposed())) {
						children.add(item.getControl());
					}
				}
