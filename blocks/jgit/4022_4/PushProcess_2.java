		if (toPush.isEmpty()) {
			Map<String
			List<RefSpec> specs = new ArrayList<RefSpec>(advertisedRefs.size());
			for (Iterator it = advertisedRefs.keySet().iterator(); it.hasNext();) {
				specs.add(new RefSpec((String) it.next()));
			}
			Collection<RemoteRefUpdate> remoteRefUpdates = null;
			try {
				remoteRefUpdates = transport.findRemoteRefUpdatesFor(specs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (remoteRefUpdates != null) {
				for (final RemoteRefUpdate rru : remoteRefUpdates) {
					if (this.toPush.put(rru.getRemoteName()
						throw new TransportException(
								MessageFormat.format(
										JGitText.get().duplicateRemoteRefUpdateIsIllegal
										rru.getRemoteName()));
				}
			}
		}
