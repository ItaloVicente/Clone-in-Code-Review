			if (f == null
					|| isSameFont(f, current.getFontRegistry().defaultFont())) {
				Font treeTableFont = current.getFontRegistry()
						.get(TREE_TABLE_FONT);
				if (treeTableFont != null) {
					f = treeTableFont;
				}
			}
