		if (oc != null) {
			if (oc.readAheadService != null)
				oc.readAheadService.shutdown();
			for (DfsPackFile pack : oc.getPackFiles())
				pack.key.cachedSize.set(0);
		}
