	public void fillTo(final RevCommit commitToLoad
			throws MissingObjectException
			IOException {
		if (walker == null || commitToLoad == null
				|| (highMark > 0 && size > highMark))
			return;

		RevCommit c = walker.next();
		if (c == null) {
			walker = null;
			return;
		}
		enter(size
		add((E) c);

		while ((highMark == 0 || size <= highMark) && !c.equals(commitToLoad)) {
			int index = size;
			Block s = contents;
			while (index >> s.shift >= BLOCK_SIZE) {
				s = new Block(s.shift + BLOCK_SHIFT);
				s.contents[0] = contents;
				contents = s;
			}
			while (s.shift > 0) {
				final int i = index >> s.shift;
				index -= i << s.shift;
				if (s.contents[i] == null)
					s.contents[i] = new Block(s.shift - BLOCK_SHIFT);
				s = (Block) s.contents[i];
			}

			final Object[] dst = s.contents;
			while ((highMark == 0 || size <= highMark) && index < BLOCK_SIZE
					&& !c.equals(commitToLoad)) {
				c = walker.next();
				if (c == null) {
					walker = null;
					return;
				}
				enter(size++
				dst[index++] = c;
			}
		}
	}

