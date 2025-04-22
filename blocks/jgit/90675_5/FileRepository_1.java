		GcLog gcLog = new GcLog(this);

		boolean background = monitor instanceof NullProgressMonitor && shouldAutoDetach();

		if (gcLog.lock(background)) {
			return;
		}

		Runnable gcTask = () -> {
			try {
					GC gc = new GC(this);
					gc.setPackConfig(new PackConfig(this));
					gc.setProgressMonitor(monitor);
					gc.setAuto(true);
					gc.gc();
					gcLog.cleanUp();
			} catch (IOException | ParseException e) {
				if (background) {
					try {
						gcLog.write(e.getMessage().getBytes());
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						gcLog.write(sw.toString().getBytes(StandardCharsets.UTF_8));
						gcLog.commit();
					} catch (IOException e2) {
						e2.addSuppressed(e);
						LOG.error(e2.getMessage()
					}
				} else {
					throw new JGitInternalException(e.getMessage()
				}
			} finally {
				gcLog.unlock();
			}
		};
		if (background) {
			Thread thread = new Thread(gcTask);
			thread.start();
		} else {
			gcTask.run();
