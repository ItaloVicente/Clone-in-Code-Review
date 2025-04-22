	private void updateMenuItemText(MenuItem mi) {
		int accelerator = 0;
		String acceleratorText = null;
		IAction updatedAction = getAction();
		String text = null;
		accelerator = updatedAction.getAccelerator();
		ExternalActionManager.ICallback callback = ExternalActionManager.getInstance().getCallback();

		if ((accelerator != 0) && (callback != null) && (callback.isAcceleratorInUse(accelerator))) {
			accelerator = 0;
		}
