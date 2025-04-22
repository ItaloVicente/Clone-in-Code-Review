			Collection<RemoteRefUpdate> updates = Transport
					.findRemoteRefUpdatesFor(repository, refSpecs,
							remoteCfg.getFetchRefSpecs());
			if (updates.isEmpty()) {
				nothingToPush(parent);
				return null;
			} else if (updates.size() > 1) {
				List<String> allLocalNames = updates.stream()
						.map(RemoteRefUpdate::getSrcRef)
						.collect(Collectors.toList());
				if (!warnMultiple(parent, allLocalNames)) {
