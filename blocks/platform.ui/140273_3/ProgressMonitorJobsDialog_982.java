				if ((System.currentTimeMillis() - watchTime) > ProgressManager.getInstance().getLongOperationTime()) {
					watchTime = -1;
					openDialog();
				}
			}

			private void openDialog() {
				if (!PlatformUI.isWorkbenchRunning()) {
