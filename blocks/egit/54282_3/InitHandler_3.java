		Shell activeShell = HandlerUtil.getActiveShell(event);
		InitDialog dialog = new InitDialog(activeShell);
		if (dialog.open() != Window.OK) {
			return null;
		}

		InitOperation initOperation = new InitOperation(gfRepo.getRepository(),
				dialog.getResult());
