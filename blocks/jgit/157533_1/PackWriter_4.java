		public boolean hasNext() {
			if (next != null) {
				return true;
			}
			while (it.hasNext()) {
				WeakReference<PackWriter> ref = it.next();
				next = ref.get();
				if (next != null) {
					return true;
