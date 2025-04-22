		}

		if (adds.size() > 0 || removes.size() > 0 || changes.size() > 0) {
			fireMapChange(new MapDiff<K, V>() {

				@Override
				public Set<K> getAddedKeys() {
					return adds;
