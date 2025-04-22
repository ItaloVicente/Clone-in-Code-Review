			SWTBotTreeItem subteeItems = bot.tree().getAllItems()[i];
			Row[] subrows = rows[i].getSubrows();
			if (subrows != null) {
				assertEquals("Row " + i + " is a tree:", subrows.length, subteeItems.getItems().length);
				assertNotNull("Rows " + i + " is not a tree", subteeItems.getItems());
				for (int j = 0; j < subrows.length; ++j) {
					Row r = subrows[j];
					assertEquals(r.isSelected(), subteeItems.getItems()[j].isChecked());
					assertEquals(r.getProject(), subteeItems.cell(j, 0));
					assertEquals(r.getPath(), subteeItems.cell(j, 1));
					assertEquals(r.getRepository(), subteeItems.cell(j, 2));
				}
			} else
				assertEquals("Row " + i + " is a tree:", 0, subteeItems.getItems().length);
