			if (adds.size() > 0 || removes.size() > 0 || changes.size() > 0) {
				fireMapChange(new MapDiff<K, V>() {

					@Override
					public Set<K> getAddedKeys() {
						return adds;
					}

					@Override
					public Set<K> getChangedKeys() {
						return changes;
					}

					@Override
					public V getNewValue(Object key) {
						return wrappedMap.get(key);
					}

					@Override
					public V getOldValue(Object key) {
						return oldValues.get(key);
					}

					@Override
					public Set<K> getRemovedKeys() {
						return removes;
					}
				});
			}
