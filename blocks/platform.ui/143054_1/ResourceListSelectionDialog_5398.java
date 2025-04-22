			if (disposed[0]) {
				return;
			}

			if (!refilter) {
				for (int i = 0; i <= lastMatch; i++) {
					if (i % 50 == 0) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
					if (stop || resourceNames.isDisposed()) {
						disposed[0] = true;
						return;
					}
					final int index = i;
					display.syncExec(() -> {
						if (stop || resourceNames.isDisposed()) {
