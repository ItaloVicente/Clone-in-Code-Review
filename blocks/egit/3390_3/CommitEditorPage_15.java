		Composite displayArea = toolkit.createComposite(body);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(displayArea);

		createHeaderArea(displayArea, toolkit, 2);
		createMessageArea(displayArea, toolkit, 2);
		createFilesArea(displayArea, toolkit, 1);
		createBranchesArea(displayArea, toolkit, 1);
