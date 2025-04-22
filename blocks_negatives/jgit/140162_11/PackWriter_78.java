		public Iterator<PackWriter> iterator() {
			return new Iterator<PackWriter>() {
				private final Iterator<WeakReference<PackWriter>> it =
						instances.keySet().iterator();
				private PackWriter next;

				@Override
				public boolean hasNext() {
					if (next != null)
						return true;
					while (it.hasNext()) {
						WeakReference<PackWriter> ref = it.next();
						next = ref.get();
						if (next != null)
							return true;
						it.remove();
					}
					return false;
				}
