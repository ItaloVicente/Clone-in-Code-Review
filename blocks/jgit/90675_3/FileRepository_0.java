		synchronized(this) {
			if (gc != null) {
				return;
			}
			gc = new GC(this);
		}
		Runnable gcTask = () -> {
			try {
				gc.setPackConfig(new PackConfig(this));
				gc.setProgressMonitor(monitor);
				gc.setAuto(true);
				gc.gc();
			} catch (IOException | ParseException e) {
				LOG.error(e.getMessage()
			} finally {
				synchronized (FileRepository.this) {
					gc = null;
				}
			}
		};
		if (monitor instanceof NullProgressMonitor) {
			Thread thread = new Thread(gcTask);
			thread.start();
		} else {
			gcTask.run();
