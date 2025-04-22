
		shell.bot().textWithLabel(UIText.RefSpecDialog_SourceBranchPushLabel)
				.setText("HEAD");
		shell.bot().textWithLabel(UIText.RefSpecDialog_DestinationPushLabel)
				.setText("refs/for/master");
		final Text text2 = shell.bot().textWithLabel(
