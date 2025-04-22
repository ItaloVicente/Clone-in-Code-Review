	private int getPreferredLastColumnWidth() {
		return Arrays.stream(table.getItems()).mapToInt(item -> {
			final String text = item.getText(1);
			textLayout.setText(text);
			return textLayout.getBounds().width;
		}).max().orElse(0) + 24;
	}

