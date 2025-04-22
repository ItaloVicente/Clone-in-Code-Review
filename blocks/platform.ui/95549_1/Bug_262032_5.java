		Display.getDefault().asyncExec(() -> {
			lock.acquire();
			concurrentAccess = true;
			tb1.setStatus(TestBarrier.STATUS_RUNNING);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			concurrentAccess = false;
			lock.release();
