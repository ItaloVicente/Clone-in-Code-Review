				if (preselect != null && preselect.equals(rev.getId())
						|| preselect == null && currentPosition < 0) {
					currentPosition = findResults.getMatchNumberFor(index);
					preselect = null;
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							notifyListeners(index);
						}
					});
				}
				if (now - lastUpdate > UPDATE_INTERVAL && !isDisposed()) {
