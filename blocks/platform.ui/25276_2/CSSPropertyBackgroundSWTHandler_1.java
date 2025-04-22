		} else if ((widget instanceof Button) || widget instanceof Label) {
			color = ((Control) widget).getBackground();
			Method drawsBackgroundMethod = widget.getClass().getDeclaredMethod(
					"drawsBackground");
			drawsBackgroundMethod.setAccessible(true);
			Boolean drawsBackground = (Boolean) drawsBackgroundMethod
					.invoke(widget);
			if (!drawsBackground) {
				return "transparent";
			}
