				Point point = trimStackTB.getDisplay().map(null, trimStackTB,
						new Point(event.x, event.y));
				ToolItem selectedToolItem = trimStackTB.getItem(point);
				if (selectedToolItem == null) {
					return;
				}
