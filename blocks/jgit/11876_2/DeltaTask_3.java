			for (;;) {
				Slice s;
				synchronized (this) {
					if (slices.isEmpty())
						break;
					s = slices.removeFirst();
				}
				doSlice(or
			}
			for (Slice s = block.stealWork(); s != null; s = block.stealWork()) {
				System.out.println(String.format("%d stole %d"
				doSlice(or
