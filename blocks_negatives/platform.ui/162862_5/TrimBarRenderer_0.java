	private class LayoutJob implements Runnable {
		public List<MTrimBar> barsToLayout = new ArrayList<>();

		@Override
		public void run() {
			layoutJob = null;
			if (barsToLayout.isEmpty())
				return;
			for (MTrimBar bar : barsToLayout) {
				Composite trimCtrl = (Composite) bar.getWidget();
				if (trimCtrl != null && !trimCtrl.isDisposed())
					trimCtrl.layout();
			}
		}
	}

	private LayoutJob layoutJob = null;

