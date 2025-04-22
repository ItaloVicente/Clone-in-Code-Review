					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (pruning) {
								IStatus status = event.getStatus();
								/*
								 * Prune the history unless we can determine
								 * that this was a cancelled attempt. See
								 */
								if (status == null
										|| status.getSeverity() != IStatus.CANCEL) {
									flush();
								}
								update();
							} else {
								update();
