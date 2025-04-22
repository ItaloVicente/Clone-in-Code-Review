
		AlternateHandle[] alt = alternates.get();
		if (alt != null) {
			alternates.set(null);
			for(final AlternateHandle od : alt)
				od.close();
		}
