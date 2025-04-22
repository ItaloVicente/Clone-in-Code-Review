			ToolBar newViewTB = null;
			if (needsTB && part != null && part.getObject() != null) {
				part.getToolbar().setVisible(true);
				newViewTB = (ToolBar) renderer.createGui(part.getToolbar(), tabFolder.getTopRight(), part.getContext());
				if (newViewTB == null) {
					adjusting = false;
					return;
				}
				newViewTB.moveAbove(null);
				newViewTB.pack();
			}
