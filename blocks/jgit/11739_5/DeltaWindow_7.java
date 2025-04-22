			for (;;) {
				ObjectToPack next;
				synchronized (this) {
					if (end <= cur)
						break;
					next = toSearch[cur++];
				}
