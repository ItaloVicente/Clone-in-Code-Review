		super.setContent(content);
		if (content != null) {
			content.setForeground(getForeground());
			content.setBackground(getBackground());
			content.setFont(getFont());
		}
	}
