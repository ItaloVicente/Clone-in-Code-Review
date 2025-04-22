			if (timeInMillis <= 0) {
				while (Display.getCurrent().readAndDispatch()) {
				}
			} else {
				long start = System.currentTimeMillis();
				while (System.currentTimeMillis() - start <= timeInMillis) {
					while (Display.getCurrent().readAndDispatch()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							break;
						}
					}
				}
