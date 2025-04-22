			buf.position(0);
			int n = read(rc
			if (n <= 0) {
				throw packfileIsTruncated();
			} else if (n > remaining) {
				n = (int) remaining;
