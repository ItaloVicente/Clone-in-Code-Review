	@Test
	public void testCancelWhileEditing() throws Exception {
		SWTBotTable table = dialogBot.bot().table();
		table.getTableItem(5).select();
		click(UIText.GitHistoryPage_filterRefDialog_button_edit);

		bot.text(0).setText("edited");

		clickCancel();
		bot.waitUntil(Conditions.shellCloses(dialogBot));

		verify(refFilterHelper, Mockito.never())
				.setRefFilters(ArgumentMatchers.any());
	}

