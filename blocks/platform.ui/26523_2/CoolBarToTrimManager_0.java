		ArrayList<MToolBarElement> toRemove = new ArrayList<MToolBarElement>();
		for (MTrimElement child : topTrim.getChildren()) {
			if (child instanceof MToolBar) {
				MToolBar toolbar = (MToolBar) child;
				for (MToolBarElement element : toolbar.getChildren()) {
					if (OpaqueElementUtil.isOpaqueToolItem(element)) {
						toRemove.add(element);
					}
				}
				if (!toRemove.isEmpty()) {
					toolbar.getChildren().removeAll(toRemove);
					toRemove.clear();
				}
			}
		}
