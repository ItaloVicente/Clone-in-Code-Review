	static class CacheKey {
		final String id;
		final MUIElement searchRoot;
		final int hashCode = Integer.MIN_VALUE;

		CacheKey(String id, MUIElement searchRoot) {
			if (id == null || searchRoot == null) {
				throw new IllegalArgumentException();
			}
			this.id = id;
			this.searchRoot = searchRoot;

			final int prime = 31;
			int hashCode = 1;
			hashCode = prime * hashCode + ((id == null) ? 0 : id.hashCode());
			hashCode = prime * hashCode + ((searchRoot == null) ? 0 : searchRoot.hashCode());
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (!id.equals(other.id))
				return false;
			if (!searchRoot.equals(other.searchRoot))
				return false;
			return true;
		}
	}

	@SuppressWarnings("javadoc")
	public static class CacheAdapter extends EContentAdapter {
		private static final Object NULL = new Object();
		private final Map<Object, Object> values = new ConcurrentHashMap<>(500);
		private final Collection<Listener> listeners = new LinkedHashSet<>();
		private volatile boolean empty = true;

		public static interface Listener {
			void onEvict(CacheAdapter cache);
		}

		public static CacheAdapter getOrCreate(Resource resource) {
			CacheAdapter adapter = (CacheAdapter) EcoreUtil.getAdapter(resource.eAdapters(), CacheAdapter.class);
			if (adapter == null) {
				adapter = new CacheAdapter();
				resource.eAdapters().add(adapter);
			}
			return adapter;
		}

		public void set(Object name, Object value) {
			empty = false;
			if (value != null)
				this.values.put(name, value);
			else
				this.values.put(name, NULL);
		}

		@SuppressWarnings("unchecked")
		private <T> T internalGet(Object name) {
			if (empty)
				return null;
			return (T) this.values.get(name);

		}

		public <T> T get(Object name) {
			T result = internalGet(name);
			if (result != NULL)
				return result;
			return null;
		}

		public void addCacheListener(Listener listener) {
			this.listeners.add(listener);
		}

		public void removeCacheListener(Listener listener) {
			this.listeners.remove(listener);
		}

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (isSemanticStateChange(notification)) {
				clearValues();
			}
		}

		public void clearValues() {
			if (!empty) {
				values.clear();
				empty = true;
				listeners.forEach(l -> l.onEvict(this));
			}
		}

		private boolean isSemanticStateChange(Notification notification) {
			if (notification.isTouch()) {
				return false;
			}
			if (notification.getNewValue() instanceof Diagnostic || notification.getOldValue() instanceof Diagnostic) {
				return false;
			}
			if (!(notification.getFeature() instanceof EReference)) {
				return false;
			}
			EReference reference = (EReference) notification.getFeature();
			if (notification.getEventType() == Notification.REMOVE_MANY
					&& notification.getOldValue() instanceof Collection
					&& ((Collection<?>) notification.getOldValue()).isEmpty()) {
				return false;
			}
			if (!reference.isContainment()) {
				return false;
			}
			if (UiPackageImpl.Literals.ELEMENT_CONTAINER__CHILDREN == reference
					|| UiPackageImpl.Literals.UI_ELEMENT__PARENT == reference) {
				return true;
			}
			if (ApplicationPackageImpl.Literals.APPLICATION_ELEMENT__TRANSIENT_DATA == reference
					|| UiPackageImpl.Literals.ELEMENT_CONTAINER__SELECTED_ELEMENT == reference) {
				return false;
			}
			return true;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == getClass();
		}

		@Override
		protected boolean resolve() {
			return false;
		}

		@Override
		protected boolean useRecursion() {
			return false;
		}
	}

