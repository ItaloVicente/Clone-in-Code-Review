		if (tooltipText == null || tooltipText.isEmpty()) {
			decorator = UIUtils.addBulbDecorator(text, null);
		} else {
			decorator = UIUtils.addBulbDecorator(text,
					MessageFormat.format(tooltipText, stroke.format()));
		}
