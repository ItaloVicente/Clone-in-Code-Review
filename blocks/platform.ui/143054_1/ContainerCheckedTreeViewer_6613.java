	private void updateParentItems(TreeItem item) {
		if (item != null) {
			Item[] children = getChildren(item);
			boolean containsChecked = false;
			boolean containsUnchecked = false;
			for (Item element : children) {
				TreeItem curr = (TreeItem) element;
				boolean currChecked = curr.getChecked();
				containsChecked |= currChecked;
				if (!containsUnchecked) {
					containsUnchecked = !currChecked || curr.getGrayed();
				}
				if (containsChecked && containsUnchecked) {
					break;
				}
			}

			boolean updated = false;
			boolean checked = item.getChecked();
			if (checked != containsChecked) {
				item.setChecked(containsChecked);
				updated = true;
			}
			boolean grayed = item.getGrayed();
			boolean newGrayed = containsChecked && containsUnchecked;
			if (grayed != newGrayed) {
				item.setGrayed(newGrayed);
				updated = true;
			}

			if (updated) {
				updateParentItems(item.getParentItem());
			}
		}
	}
