		String clipText;
		try {
			clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
		} finally {
			clipboard.dispose();
		}
