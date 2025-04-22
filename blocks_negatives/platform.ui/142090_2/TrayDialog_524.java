				int newWidth = shell.getSize().x;
				if (newWidth != shellWidth) {
					int shellWidthIncrease = newWidth - shellWidth;
					int trayWidthIncreaseTimes100 = (shellWidthIncrease * TRAY_RATIO) + remainder;
					int trayWidthIncrease = trayWidthIncreaseTimes100/100;
					remainder = trayWidthIncreaseTimes100 - (100 * trayWidthIncrease);
					data.widthHint = data.widthHint + trayWidthIncrease;
					shellWidth = newWidth;
					if (!shell.isDisposed()) {
						shell.layout();
					}
