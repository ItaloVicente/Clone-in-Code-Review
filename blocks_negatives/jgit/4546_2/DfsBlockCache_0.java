		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack != pack || r.position != position)
				continue;
			T v = r.get();
			if (v == null)
				return null;
			statHit.incrementAndGet();
			return v;
		}
		return null;
