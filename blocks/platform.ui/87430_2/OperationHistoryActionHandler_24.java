					display.asyncExec(() -> {
						if (pruning) {
							IStatus status = event.getStatus();
							if (status == null
									|| status.getSeverity() != IStatus.CANCEL) {
								flush();
