		synchronized (this) {
			if (elements == null) {
				elements = new LinkedList<>();
				if (initializer != null) {
					elements.addAll(initializer.get());
				}
			}
		}
