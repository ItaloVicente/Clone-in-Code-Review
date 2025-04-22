		menuTB.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				if (e.childID != ACC.CHILDID_SELF) {
					Accessible accessible = (Accessible) e.getSource();
					ToolBar toolBar = (ToolBar) accessible.getControl();
					if (0 <= e.childID && e.childID < toolBar.getItemCount()) {
						ToolItem item = toolBar.getItem(e.childID);
						if (item != null) {
							e.result = item.getToolTipText();
						}
