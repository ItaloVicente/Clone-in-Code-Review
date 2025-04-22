		IntList map = new IntList((end - ptr) / 36);
		map.add(Integer.MIN_VALUE);
		boolean foundLF = true;
		for (; ptr < end; ptr++) {
			if (foundLF) {
				map.add(ptr);
			}

			if (buf[ptr] == '\0') {
				map = new IntList(3);
				map.add(Integer.MIN_VALUE);
				map.add(start);
				break;
			}

			foundLF = (buf[ptr] == '\n');
		}
