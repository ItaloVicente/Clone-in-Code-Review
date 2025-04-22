
		String rootText = labelProvider.getStyledText(root).getString();
		String symrefsText = labelProvider.getStyledText(symrefsnode)
				.getString();
		SWTBotTreeItem symrefsitem = TestUtil.navigateTo(tree, rootText,
				symrefsText);
