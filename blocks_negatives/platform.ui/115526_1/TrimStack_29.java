			if (minimizedElement.getWidget() != null) {
				trimStackTB.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateTrimStackItems();
					}
				});
			}
