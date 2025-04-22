	}

	private Point getInitialSize(String dialogSettingsSectionName, Point initialSize) {
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		try {
			int x, y;
			x = settings.getInt(DIALOG_WIDTH);
			y = settings.getInt(DIALOG_HEIGHT);
			return new Point(Math.max(x, initialSize.x), Math.max(y, initialSize.y));
		} catch (NumberFormatException e) {
		}
		return initialSize;
	}

	@Override
