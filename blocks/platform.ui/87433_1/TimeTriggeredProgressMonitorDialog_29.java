        Runnable dialogWaitRunnable = () -> {
			try {
				TimeTriggeredProgressMonitorDialog.super.run(fork, cancelable, runnable);
			} catch (InvocationTargetException e1) {
				invokes[0] = e1;
			} catch (InterruptedException e2) {
				interrupt[0]= e2;
			}
		};
