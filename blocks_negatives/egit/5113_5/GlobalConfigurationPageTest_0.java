	public void testChangeValue() throws Exception {
		config.setString(TESTSECTION, null, TESTNAME, "true");
		config.save();
		getGitConfigurationPreferencePage();
		preferencePage.bot().tree(1).getTreeItem(TESTSECTION).getNode(TESTNAME)
				.select();
		String text = preferencePage.bot().textWithLabel(
				UIText.ConfigurationEditorComponent_ValueLabel).getText();
		assertEquals("true", text);
		preferencePage.bot().textWithLabel(
				UIText.ConfigurationEditorComponent_ValueLabel)
				.setText("false");
		bot.button(UIText.ConfigurationEditorComponent_ChangeButton).click();
		preferencePage.bot().tree(1).getTreeItem(TESTSECTION).getNode(TESTNAME)
				.select();
		text = preferencePage.bot().textWithLabel(
				UIText.ConfigurationEditorComponent_ValueLabel).getText();
		assertEquals("false", text);
		List<String> list = new ArrayList<String>(1);
		list.add("first");
		list.add("second");
		config.setStringList(TESTSECTION, null, TESTNAME, list);
		config.save();
		getGitConfigurationPreferencePage();
		preferencePage.bot().tree(1).getTreeItem(TESTSECTION).getNode(
				TESTNAME + "[1]").select();
		text = preferencePage.bot().textWithLabel(
				UIText.ConfigurationEditorComponent_ValueLabel).getText();
		assertEquals("second", text);
		preferencePage.bot().textWithLabel(
				UIText.ConfigurationEditorComponent_ValueLabel).setText("new");
		bot.button(UIText.ConfigurationEditorComponent_ChangeButton).click();
		preferencePage.bot().button(IDialogConstants.OK_LABEL).click();
		config.load();
		assertEquals(1, Arrays.asList(
				config.getStringList(TESTSECTION, null, TESTNAME)).indexOf(
				"new"));
	}

	@Test
	public void testAddValue() throws Exception {
