
	private static SWTBotTableItem getTableItemWithId(SWTBotTable table,
			ObjectId wantedId) {
		for (int i = 0; i < table.rowCount(); i++) {
			String id = table.cell(i, UIText.CommitGraphTable_CommitId);
			String idWithoutEllipsis = id.substring(0, 7);
			if (wantedId.getName().startsWith(idWithoutEllipsis))
				return table.getTableItem(i);
		}

		throw new IllegalStateException("TableItem for commit with ID " + wantedId + " not found.");
	}
