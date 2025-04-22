			long now = System.currentTimeMillis();
			if (now < lastUpdatedAt || now - lastUpdatedAt > UPDATE_INTERVAL) {
				root.subTask(msg + ", " + cmp); //$NON-NLS-1$
				root.setWorkRemaining(100);
				root.worked(1);
				lastUpdatedAt = now;
			}
		} else if (lastShown == 0
				|| cmp * 100 / totalWork != lastShown * 100 / totalWork) {
