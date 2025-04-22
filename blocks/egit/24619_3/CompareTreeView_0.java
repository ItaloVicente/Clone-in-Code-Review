				long currentTimeMilliseconds = System.currentTimeMillis();
				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					long size1 = tw.getObjectReader().getObjectSize(
							compareVersionIterator.getEntryObjectId(),
							Constants.OBJ_BLOB);
					long size2 = tw.getObjectReader().getObjectSize(
							baseVersionIterator.getEntryObjectId(),
							Constants.OBJ_BLOB);
					final long REPORTSIZE = 100000;
					if (size1 > REPORTSIZE || size2 > REPORTSIZE) {
						monitor.setTaskName(currentPath.toString());
						previousTimeMilliseconds = currentTimeMilliseconds;
					}
				} else if (currentTimeMilliseconds - previousTimeMilliseconds > 500) {
					monitor.setTaskName(currentPath.toString());
					previousTimeMilliseconds = currentTimeMilliseconds;
				}

