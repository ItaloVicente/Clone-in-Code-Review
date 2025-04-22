				int start = toolBar.getItemCount();
				src.fill(toolBar, destIx);
				int newItems = toolBar.getItemCount() - start;
				for (int i = 0; i < newItems; i++) {
					ToolItem item = toolBar.getItem(destIx++);
					item.setData(src);
				}
			}
