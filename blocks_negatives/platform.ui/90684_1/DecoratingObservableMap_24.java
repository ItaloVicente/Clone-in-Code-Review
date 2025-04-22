			mapChangeListener = new IMapChangeListener<K, V>() {
				@Override
				public void handleMapChange(MapChangeEvent<? extends K, ? extends V> event) {
					DecoratingObservableMap.this.handleMapChange(event);
				}
			};
