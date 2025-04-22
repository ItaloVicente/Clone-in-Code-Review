
		if(visible)
			getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					updateCleanItems();
				}
			});
