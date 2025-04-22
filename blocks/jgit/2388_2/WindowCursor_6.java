	public void copyPackAsIs(PackOutputStream out
			throws IOException {
		((LocalCachedPack) pack).copyAsIs(out
	}

	void copyPackAsIs(final PackFile pack
			long position
		while (0 < cnt) {
			pin(pack

			int ptr = (int) (position - window.start);
			int n = (int) Math.min(window.size() - ptr
			window.write(out
			position += n;
			cnt -= n;
		}
	}

