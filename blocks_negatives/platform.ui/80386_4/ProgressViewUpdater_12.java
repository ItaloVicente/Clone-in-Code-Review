				if (currentInfo.updateAll) {
					synchronized (updateLock) {
						currentInfo.reset();
					}
					for (int i = 0; i < collectors.length; i++) {
						collectors[i].refresh();
					}
