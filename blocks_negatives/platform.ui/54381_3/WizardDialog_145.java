			BusyIndicator.showWhile(getContents().getDisplay(), new Runnable() {
				@Override
				public void run() {
					updateForPage(finalPage);
				}
			});
