		runOnThread(() -> {
			try {
				pm.start(1);
				fail("start did not fail on background thread");
			} catch (IllegalStateException notMainThread) {
			}

			try {
				pm.beginTask("title"
				fail("beginTask did not fail on background thread");
			} catch (IllegalStateException notMainThread) {
			}

			try {
				pm.endTask();
				fail("endTask did not fail on background thread");
			} catch (IllegalStateException notMainThread) {
