            toolItem.addSelectionListener(widgetSelectedAdapter(event -> {
			    menuManager.update(true);
			    Point point = new Point(event.x, event.y);
			    if (event.widget instanceof ToolItem) {
			        ToolItem toolItem = (ToolItem) event.widget;
			        Rectangle rectangle = toolItem.getBounds();
			        point = new Point(rectangle.x, rectangle.y
			                + rectangle.height);
			    }
			    Menu menu = menuManager.createContextMenu(parent);
			    point = parent.toDisplay(point);
			    menu.setLocation(point.x, point.y);
			    menu.setVisible(true);
			}));
