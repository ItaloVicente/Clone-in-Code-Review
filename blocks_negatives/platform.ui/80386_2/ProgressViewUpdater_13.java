				} else {
					Object[] updateItems;
					Object[] additionItems;
					Object[] deletionItems;
					synchronized (updateLock) {
						currentInfo.processForUpdate();
