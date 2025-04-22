		Version jFaceVersion = Platform.getBundle("org.eclipse.jface")
				.getVersion();
		if (jFaceVersion.compareTo(Version.valueOf("3.22.0")) < 0
				|| jFaceVersion.compareTo(Version.valueOf("3.23.0")) >= 0) {
			bot.waitUntil(waitForWidget(allOf(widgetOfType(Text.class),
					withText(" " + errorMessage))), 20000);
		} else {
			bot.waitUntil(waitForWidget(allOf(widgetOfType(Label.class),
					withText(" " + errorMessage))), 20000);
		}
