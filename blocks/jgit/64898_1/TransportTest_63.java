
		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(Collections.nCopies(1
					new RefSpec("+refs/heads/a:refs/heads/a")));
		}
