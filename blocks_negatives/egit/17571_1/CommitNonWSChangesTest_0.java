		SWTBotTable table = commitDialog.bot().table();
		assertEquals("Wrong row count", 4, table.rowCount());
		assertTableLineContent(table, 0, "Rem., not staged",
				"GeneralProject/.project");
		assertTableLineContent(table, 1, "Rem., not staged",
				"GeneralProject/folder/test.txt");
		assertTableLineContent(table, 2, "Rem., not staged",
				"GeneralProject/folder/test2.txt");
		assertTableLineContent(table, 3, "Untracked",
				"ProjectWithoutDotProject/.project");
