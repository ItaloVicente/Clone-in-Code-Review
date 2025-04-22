		int length = 0;
		for (int i = 0; i < all.length; i++)
			if (!all[i].isLinked()) length++;
		final Entry[] r = new Entry[length];
		for (int i = 0, j = 0; i < all.length; i++)
			if (!all[i].isLinked())
				r[j++] = new ResourceEntry(all[i]);
