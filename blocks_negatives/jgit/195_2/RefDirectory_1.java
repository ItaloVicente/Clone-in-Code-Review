	@Override
	public void link(String name, String target) throws IOException {
		long m = write(fileFor(name), encode(SYMREF + target + '\n'));

		synchronized (cacheLock) {
			RefHolder holder = cache.get(name);
			if (holder != null)
				name = holder.currRef.getName();
			else {
				holder = new RefHolder();
				cache.put(name, holder);
			}

			holder.currRef = newSymbolicRef(name, target);
			holder.looseModified = m;
			modCnt++;
		}
		fireRefsChanged();
	}

