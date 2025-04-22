		for (int idx = 0; idx < controls.length; idx++) {
			if (idx < caches.length) {
				newCache[idx] = caches[idx];
				newCache[idx].setControl(controls[idx]);
			} else {
				newCache[idx] = new SizeCache(controls[idx]);
			}
		}
