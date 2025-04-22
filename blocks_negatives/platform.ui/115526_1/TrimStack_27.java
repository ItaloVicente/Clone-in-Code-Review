			if (changedObj == minimizedElement) {
				trimStackTB.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateTrimStackItems();
					}
				});
			}
