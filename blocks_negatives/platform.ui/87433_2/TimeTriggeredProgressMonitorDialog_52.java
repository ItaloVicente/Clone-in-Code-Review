        Runnable dialogWaitRunnable = new Runnable() {
    		@Override
			public void run() {
    			try {
    				TimeTriggeredProgressMonitorDialog.super.run(fork, cancelable, runnable);
    			} catch (InvocationTargetException e) {
    				invokes[0] = e;
    			} catch (InterruptedException e) {
    				interrupt[0]= e;
    			}
    		}
        };
