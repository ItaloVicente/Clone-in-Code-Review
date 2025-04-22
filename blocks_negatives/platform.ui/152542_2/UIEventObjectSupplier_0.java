				uiSync.syncExec(new Runnable() {
					@Override
					public void run() {
						requestor.execute();
					}
				});
