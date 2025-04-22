			uiSync.syncExec(new Runnable() {

				@Override
				public void run() {
					eventHandler.handleEvent(event);
				}
			});
