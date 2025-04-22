
		wiring.refreshBundles(asList(bundles), listener);

		synchronized (flag) {
			while (!flag[0]) {
				try {
					flag.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
