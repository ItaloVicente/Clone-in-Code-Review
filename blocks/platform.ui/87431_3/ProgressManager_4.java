		Runnable dialogWaitRunnable = () -> {
			try {
				dialog.setOpenOnRun(false);
				setUserInterfaceActive(false);
				dialog.run(true, true, runnable);
			} catch (InvocationTargetException e1) {
				invokes[0] = e1;
			} catch (InterruptedException e2) {
				interrupt[0] = e2;
			} finally {
				setUserInterfaceActive(true);
