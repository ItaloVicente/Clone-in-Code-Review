		applyDialogFont(container);
		container.pack();
		commitText.setFocus();
		Image titleImage = UIIcons.WIZBAN_CONNECT_REPO.createImage();
		UIUtils.hookDisposal(parent, titleImage);
		setTitleImage(titleImage);
		setTitle(UIText.CommitDialog_Title);
		setMessage(UIText.CommitDialog_Message, IMessageProvider.INFORMATION);
