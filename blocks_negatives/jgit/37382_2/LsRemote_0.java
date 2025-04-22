		final Transport tn = Transport.open(db, remote);
		if (0 <= timeout)
			tn.setTimeout(timeout);
		final FetchConnection c = tn.openFetch();
		try {
			for (final Ref r : c.getRefs()) {
				show(r.getObjectId(), r.getName());
				if (r.getPeeledObjectId() != null)
					show(r.getPeeledObjectId(), r.getName() + "^{}"); //$NON-NLS-1$
