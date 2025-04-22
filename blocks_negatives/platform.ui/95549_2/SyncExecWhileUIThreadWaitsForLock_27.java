					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							try {
								if (lock.acquire(60000)) {
									lockAcquired[0] = true;
									lock.release();
								}
							} catch (InterruptedException e) {
