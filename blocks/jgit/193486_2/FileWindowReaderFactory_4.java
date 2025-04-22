			r = createPanamaFileWindowReader(pack);
			if (r == null) {
				return new MmapFileWindowReader(pack);
			}
		} else {
			r = new HeapFileWindowReader(pack);
