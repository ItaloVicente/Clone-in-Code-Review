			Display.getCurrent().asyncExec(new Runnable() {

				@Override
				public void run() {
					if (fStyledText != null && !fStyledText.isDisposed()) {
						fStyledText.redraw();
					}
