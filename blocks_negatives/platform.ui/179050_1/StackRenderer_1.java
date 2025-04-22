		CTabItem tabItem = findItemForPart(child, parentElement);
		if (tabItem == tabFolder.getSelection()) {
			if (tabFolder.getItemCount() == 1) {
				adjustTopRight(tabFolder);
			}
		}

