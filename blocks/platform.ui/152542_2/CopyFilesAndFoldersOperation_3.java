			};
			dialog.setBlockOnOpen(true);
			dialog.open();
			if (dialog.getReturnCode() == Window.CANCEL) {
				returnValue[0] = null;
			} else {
				returnValue[0] = dialog.getValue();
