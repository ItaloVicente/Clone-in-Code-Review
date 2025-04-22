			toolItem.setText(""); //$NON-NLS-1$
			toolItem.setToolTipText(WorkbenchMessages.PerspectiveBarNewContributionItem_toolTip);
			toolItem.addSelectionListener(widgetSelectedAdapter(event -> {
				menuManager.update(true);
				Point point = new Point(event.x, event.y);
				if (event.widget instanceof ToolItem) {
					ToolItem toolItem = (ToolItem) event.widget;
					Rectangle rectangle = toolItem.getBounds();
