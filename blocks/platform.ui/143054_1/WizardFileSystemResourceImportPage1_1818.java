					throws InterruptedException {
				if (files == null) {
					throw new InterruptedException();
				}
				for (Object file : files) {
					if (monitor.isCanceled()) {
