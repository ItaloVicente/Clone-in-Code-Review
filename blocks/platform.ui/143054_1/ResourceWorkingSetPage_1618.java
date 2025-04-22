			tree.setCheckedElements(items);
			for (Object i : items) {
				IAdaptable item = null;
				if (!(i instanceof IAdaptable)) {
					continue;
				}
				item = (IAdaptable) i;
