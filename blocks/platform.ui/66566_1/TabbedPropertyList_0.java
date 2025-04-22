		RGB infoBackground;
		if (Platform.WS_GTK.equals(Platform.getWS())) {
			infoBackground = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB();
		} else {
			infoBackground = Display.getCurrent().getSystemColor(SWT.COLOR_INFO_BACKGROUND).getRGB();
		}
