				thrown.expect(TransportException.class);
				thrown.expectMessage(
						"filter requires server to advertise that capability");

				tn.fetch(NullProgressMonitor.INSTANCE,
						Collections.singletonList(new RefSpec(commit.name())));
