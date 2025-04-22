		final int nTree = walk.getTreeCount();
		while (walk.next()) {
			for (int i = 1; i < nTree; i++)
				outw.print(':');
			for (int i = 0; i < nTree; i++) {
				final FileMode m = walk.getFileMode(i);
				final String s = m.toString();
				for (int pad = 6 - s.length(); pad > 0; pad--)
					outw.print('0');
				outw.print(s);
				outw.print(' ');
			}
