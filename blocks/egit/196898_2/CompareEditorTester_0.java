	public static CompareEditorTester withTitleContaining(String title) {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotEditor editor = bot.editor(new CompareEditorTitleMatcher(title));
		return new CompareEditorTester(editor);
	}

