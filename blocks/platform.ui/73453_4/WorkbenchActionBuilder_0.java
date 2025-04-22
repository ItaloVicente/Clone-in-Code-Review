		menu.add(openWorkspaceAction);
		Separator lastSep = new Separator();
		lastSep.setId(ID_FILE_MENU_LAST_SEPARATOR);
		menu.add(lastSep);
		lastSep.setVisible(!Util.isMac());

