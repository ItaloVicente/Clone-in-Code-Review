		for (int end = off + cnt; off < end; off++) {
			monitor.update(1);

			res = window[resSlot];
			res.set(toSearch[off]);

			if (res.object.isDoNotDelta()) {
				keepInWindow();
			} else {
				search();
