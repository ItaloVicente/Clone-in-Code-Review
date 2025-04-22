		Clipboard clipboard = new Clipboard(
				PlatformUI.getWorkbench().getDisplay());
		String clipText;
		try {
			clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
		} finally {
			clipboard.dispose();
		}

