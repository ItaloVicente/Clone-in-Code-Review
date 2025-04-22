				superMonitor.beginTask(name, totalWork);
				checkTicking();
			}

			private void checkTicking() {
				if (watchTime < 0) {
