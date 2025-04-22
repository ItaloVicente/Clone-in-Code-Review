        fontChangeButton.addSelectionListener(widgetSelectedAdapter(event -> {
			Display display = event.display;
			if (isFontSelected())
				editFont(display);
			else if (isColorSelected())
				editColor(display);
			updateControls();
		}));
