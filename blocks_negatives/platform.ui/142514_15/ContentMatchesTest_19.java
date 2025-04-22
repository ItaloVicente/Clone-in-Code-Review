		Text text = searchField.getQuickAccessSearchText();
		if (text != null){
			text.setText("");
		}
		Shell shell = searchField.getQuickAccessShell();
		if (shell != null){
			shell.setVisible(false);
		}
