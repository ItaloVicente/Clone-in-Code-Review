				if ((System.currentTimeMillis() - watchTime) > progressService.getLongOperationTime()) {
					watchTime = -1;
					openDialog();
				}
			}

			private void openDialog() {
				if (!PlatformUI.isWorkbenchRunning()) {
