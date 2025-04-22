		try {
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					page.doPush(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
