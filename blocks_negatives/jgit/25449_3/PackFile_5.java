
		ObjectLoader large(PackFile pack, WindowCursor wc) {
			Delta d = this;
			while (d.next != null)
				d = d.next;
			return d.newLargeLoader(pack, wc);
		}

		private ObjectLoader newLargeLoader(PackFile pack, WindowCursor wc) {
			return new LargePackedDeltaObject(deltaPos, basePos, hdrLen,
					pack, wc.db);
		}
