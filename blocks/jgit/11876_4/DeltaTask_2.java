			for (;;) {
				Slice s;
				synchronized (this) {
					if (slices.isEmpty())
						break;
					s = slices.removeFirst();
				}
				doSlice(or
