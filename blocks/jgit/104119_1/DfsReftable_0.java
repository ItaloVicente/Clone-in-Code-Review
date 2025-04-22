		if (desc.getPackSource() == INSERT   ) {
			long min = desc.getMinUpdateIndex();
			long max = desc.getMaxUpdateIndex();
			if (min == max && min > 0) {
				return new ReftableReader(new CacheSource(this
			}
		}
