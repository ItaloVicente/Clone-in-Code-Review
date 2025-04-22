		if (map == null) {
			return;
		}

		RefCount count = map.get(descriptor);
		if (count != null) {
			count.count--;
			if (count.count == 0) {
				deallocate(count.resource, descriptor);
				map.remove(descriptor);
			}
		}

		if (map.isEmpty()) {
			map = null;
		}
	}

	@Override
