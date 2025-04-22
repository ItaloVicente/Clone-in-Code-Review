				long currentTimeMilliseconds = System.currentTimeMillis();
				long size1 = -1;
				long size2 = -1;
				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					size1 = getEntrySize(tw, compareVersionIterator);
					size2 = getEntrySize(tw, baseVersionIterator);
				}
				final long REPORTSIZE = 100000;
				if (size1 > REPORTSIZE
						|| size2 > REPORTSIZE
						|| currentTimeMilliseconds - previousTimeMilliseconds > 500) {
					monitor.setTaskName(currentPath.toString());
					previousTimeMilliseconds = currentTimeMilliseconds;
				}

