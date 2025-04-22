			r = createPanamaFileWindowReader(pack);
			if (r == null) {
				r = new MmapNioFileWindowReader(pack);
			}
		} else {
			r = new HeapFileWindowReader(pack);
