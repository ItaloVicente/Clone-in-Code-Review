				asyncExec(new Runnable() {
					@Override
					public void run() {
						enableAllWidgets(true);
						if (event.getResult().isOK()) {
							commitMessageText.setText(EMPTY_STRING);
						}
