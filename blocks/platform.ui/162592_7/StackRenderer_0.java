			boolean hasTB = part != null && part.getToolbar() != null;
			boolean needsTB = hasTB && part.getToolbar().isToBeRendered();

			if (needsTB && part.getObject() != null) {
				Control parent = part.getToolbar().isVisible() ? tabFolder.getTopRight() : limboShell;
				ToolBar newViewTB = (ToolBar) renderer.createGui(part.getToolbar(), parent, part.getContext());
