			if (flushCache) {
				bodyCache.flush();
				headCache.flush();
			}
			bodyCache.setControl(body);
			headCache.setControl(head);
