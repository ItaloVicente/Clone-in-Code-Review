			if (0 < n) {
				cnt += n;
				off += n;
				len -= n;
			} else if (in == EOF)
				return 0 < cnt ? cnt : -1;
			else {
