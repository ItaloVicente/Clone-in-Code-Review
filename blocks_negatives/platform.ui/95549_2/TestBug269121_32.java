					dialog.getShell().getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							dialog.getProgressMonitor().setCanceled(true);
						}
					});
