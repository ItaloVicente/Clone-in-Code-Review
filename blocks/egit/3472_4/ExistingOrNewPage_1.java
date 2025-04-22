	public void assertTableContents(String[][] contents){
		assertEquals(contents.length, bot.table().rowCount());
		for (int i = 0; i<contents.length; i++){
			String[] testStrings = contents[i];
			assertEquals(testStrings.length, bot.table().columnCount());
			for (int j = 0; j<testStrings.length; j++){
				assertEquals(testStrings[j], bot.table().cell(i, j));
			}
		}
	}

