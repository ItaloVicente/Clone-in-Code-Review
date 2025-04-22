					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!filterText.isDisposed()) {
								if (getInitialText().equals(
										filterText.getText().trim())) {
									filterText.selectAll();
								}
