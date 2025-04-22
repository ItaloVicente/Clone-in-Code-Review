			outThread = new StreamCopyThread(process.getInputStream()
					stdout.getRawStream());
			outThread.start();
			try {
				if (process.waitFor() == 0) {
					return stdout.toString();
				} else {
				}
			} catch (InterruptedException e) {
