			if (getSplash() != null) {

				final Throwable[] error = new Throwable[1];
				Thread initThread = new Thread() {
					@Override
					public void run() {
						try {
							UISynchronizer.startupThread.set(Boolean.TRUE);
							initOK[0] = Workbench.this.init();
						} catch (Throwable e) {
							error[0] = e;
						} finally {
							initDone = true;
							yield();
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
							}
							display.wake();
						}
					}
				};
				initThread.start();
				while (true) {
					if (!display.readAndDispatch()) {
						if (initDone)
							break;
						display.sleep();
					}
				}
				Throwable throwable = error[0];
				if (throwable != null) {
					if (throwable instanceof Error)
						throw (Error) throwable;
					if (throwable instanceof Exception)
						throw (Exception) throwable;

					throw new Error(throwable);
				}
			} else {
				initOK[0] = init();

			}
