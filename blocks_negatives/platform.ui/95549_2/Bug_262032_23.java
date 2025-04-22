		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				lock.acquire();
				concurrentAccess = true;
				tb1.setStatus(TestBarrier.STATUS_RUNNING);
				try {
				Thread.sleep(1000); } catch (InterruptedException e) {/*don't care*/}
				concurrentAccess = false;
				lock.release();
			}
