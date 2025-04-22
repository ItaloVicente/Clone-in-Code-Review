			key.cachedSize.addAndGet(size);
			ref = new Ref<>(key, pos, size, v);
			ref.hot = true;
			for (;;) {
				HashEntry n = new HashEntry(clean(e2), ref);
				if (table.compareAndSet(slot, e2, n))
					break;
				e2 = table.get(slot);
			}
			addToClock(ref, 0);
		} finally {
			regionLock.unlock();
		}
		return ref;
	}
