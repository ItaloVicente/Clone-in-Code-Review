        	projectNames.getTable().getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (!projectNames.getTable().isDisposed()) {
						projectNames.reveal(checked[0]);
					}
				}
			});
