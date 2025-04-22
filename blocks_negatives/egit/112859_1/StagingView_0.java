				asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!commitMessageSection.isDisposed()) {
							updateMessage();
						}
