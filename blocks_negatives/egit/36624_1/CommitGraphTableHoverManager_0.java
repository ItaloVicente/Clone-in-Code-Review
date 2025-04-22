		TableItem item = tableViewer.getTable().getItem(new Point(e.x, e.y));
		if (item != null) {
			SWTCommit commit = (SWTCommit) item.getData();
			if (commit != null && commit.getRefCount() > 0) {
				Rectangle itemBounds = item.getBounds();
				int firstColumnWidth = tableViewer.getTable().getColumn(0)
						.getWidth();
				int relativeX = e.x - firstColumnWidth - itemBounds.x;
				for (int i = 0; i < commit.getRefCount(); i++) {
					Ref ref = commit.getRef(i);
					Point textSpan = renderer.getRefHSpan(ref);
					if ((textSpan != null)
							&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

						String hoverText = getHoverText(ref, i, commit);
						int width = textSpan.y - textSpan.x;
						Rectangle rectangle = new Rectangle(firstColumnWidth
								+ itemBounds.x + textSpan.x, itemBounds.y,
								width, itemBounds.height);
						setInformation(hoverText, rectangle);
						return;
					}
				}
			}
