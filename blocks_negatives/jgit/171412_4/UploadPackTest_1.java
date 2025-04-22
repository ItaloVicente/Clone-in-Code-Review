			up.getRevWalk()
					.assumeShallow(Collections.singleton(commit1.getId()));
			return up;
		}, null);
		uri = testProtocol.register(ctx, server);

		try (Transport tn = testProtocol.open(uri, client, "server")) {
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(commit0.name())));
		}
