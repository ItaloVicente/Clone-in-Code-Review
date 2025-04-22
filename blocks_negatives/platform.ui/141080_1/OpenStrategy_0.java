                } else if (w instanceof TableTree) {
                    TableTree table = (TableTree) w;
                    TableTreeItem item = table.getItem(new Point(e.x, e.y));
                    if (item != null) {
						table.setSelection(new TableTreeItem[] { item });
					}
                    selEvent.item = item;
                } else {
