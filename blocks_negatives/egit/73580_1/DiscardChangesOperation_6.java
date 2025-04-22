		CheckoutCommand checkoutCommand = new Git(repository).checkout();
		if (revision != null)
			checkoutCommand.setStartPoint(revision);
		if (stage != null)
			checkoutCommand.setStage(stage.checkoutStage);
			checkoutCommand.setAllPaths(true);
		else {
			for (String path : paths)
				checkoutCommand.addPath(path);
