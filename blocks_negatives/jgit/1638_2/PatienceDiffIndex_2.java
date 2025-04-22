				final int bs = bOf(rec);
				if (!cmp.equals(a, ptr, b, bs)) {
					ptr++;
					continue SCAN;
				}

