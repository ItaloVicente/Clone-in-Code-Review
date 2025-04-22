		Runnable dialogWaitRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					dialog.setOpenOnRun(false);
					setUserInterfaceActive(false);
					dialog.run(true, true, runnable);
				} catch (InvocationTargetException e) {
					invokes[0] = e;
				} catch (InterruptedException e) {
					interrupt[0] = e;
				} finally {
					setUserInterfaceActive(true);
				}
