				Display display = tv.getControl().getDisplay();
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!tv.getControl().isDisposed()) {
							refreshRepositoryList();
							checkPage();
