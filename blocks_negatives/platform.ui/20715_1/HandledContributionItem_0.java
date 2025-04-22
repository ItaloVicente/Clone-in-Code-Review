	static class RunnableRunner implements ISafeRunnable {
		private Runnable runnable;

		public void setRunnable(Runnable r) {
			runnable = r;
		}

		public void handleException(Throwable exception) {
		}

		public void run() throws Exception {
			runnable.run();
		}

	}

	public static class ToolItemUpdateTimer implements Runnable {
		Display display = Display.getCurrent();
		RunnableRunner runner = new RunnableRunner();

		List<HandledContributionItem> itemsToCheck = new ArrayList<HandledContributionItem>();
		List<Runnable> windowRunnables = new ArrayList<Runnable>();
		final List<HandledContributionItem> orphanedToolItems = new ArrayList<HandledContributionItem>();

		public void addWindowRunnable(Runnable r) {
			windowRunnables.add(r);
		}

		public void removeWindowRunnable(Runnable r) {
			windowRunnables.remove(r);
		}

		void registerItem(HandledContributionItem item) {
			if (!itemsToCheck.contains(item)) {
				itemsToCheck.add(item);

				if (itemsToCheck.size() == 1)
					display.timerExec(400, this);
			}
		}

		void removeItem(HandledContributionItem item) {
			itemsToCheck.remove(item);
		}

		public void run() {

			for (final HandledContributionItem hci : itemsToCheck) {
				if (hci.model != null && hci.model.getParent() != null) {
					hci.updateItemEnablement();
				} else {
					orphanedToolItems.add(hci);
				}
			}
			if (!orphanedToolItems.isEmpty()) {
				itemsToCheck.removeAll(orphanedToolItems);
				orphanedToolItems.clear();
			}

			if (windowRunnables.size() > 0) {
				Runnable[] array = new Runnable[windowRunnables.size()];
				windowRunnables.toArray(array);
				for (Runnable r : array) {
					runner.setRunnable(r);
					SafeRunner.run(runner);
				}
			}

			if (itemsToCheck.size() > 0)
				display.timerExec(400, this);
		}
	}

	public static ToolItemUpdateTimer toolItemUpdater = new ToolItemUpdateTimer();

