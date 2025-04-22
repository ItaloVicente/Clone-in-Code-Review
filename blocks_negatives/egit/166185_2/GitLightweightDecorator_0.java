		display.syncExec(new Runnable() {
			@Override
			public void run() {
				ITheme theme  = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
				for (String actColor : actColors) {
					theme.getColorRegistry().get(actColor);
