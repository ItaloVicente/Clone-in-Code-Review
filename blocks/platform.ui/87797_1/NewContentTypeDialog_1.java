		setTitle(WorkbenchMessages.ContentTypes_newContentTypeDialog_title);
		setMessage(WorkbenchMessages.ContentTypes_newContentTypeDialog_descritption);

		Dialog.applyDialogFont(parentComposite);

		Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y)
				.generateLayout(contents);

		return contents;
