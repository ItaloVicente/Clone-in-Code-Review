		if (force) {
			final List<RefSpec> orig = new ArrayList<RefSpec>(refSpecs);
			refSpecs.clear();
			for (final RefSpec spec : orig)
				refSpecs.add(spec.setForceUpdate(true));
		}

		final List<Transport> transports;
		transports = Transport.openAll(db, remote, Transport.Operation.PUSH);
		for (final Transport transport : transports) {
			if (0 <= timeout)
				transport.setTimeout(timeout);
			transport.setPushThin(thin);
			if (receivePack != null)
				transport.setOptionReceivePack(receivePack);
			transport.setDryRun(dryRun);

			final Collection<RemoteRefUpdate> toPush = transport
					.findRemoteRefUpdatesFor(refSpecs);

			final URIish uri = transport.getURI();
			final PushResult result;
			try {
				result = transport.push(new TextProgressMonitor(), toPush);
			} finally {
				transport.close();
			}

