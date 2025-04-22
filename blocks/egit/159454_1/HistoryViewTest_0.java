				StringBuilder b = new StringBuilder(
						"CommitGraphTable did not become empty\n");
				for (int i = 0; i < table.rowCount(); i++) {
					b.append(table.getTableItem(i).getText() + "\n");
				}
				return b.toString();
