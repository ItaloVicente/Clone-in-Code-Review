			Object obj = itemModel.getParent();
			if (!(obj instanceof MMenu)) {
				return;
			}
			MenuManager parent = getManager((MMenu) obj);
			if (itemModel.isToBeRendered()) {
				if (parent != null) {
					modelProcessSwitch(parent, itemModel);
