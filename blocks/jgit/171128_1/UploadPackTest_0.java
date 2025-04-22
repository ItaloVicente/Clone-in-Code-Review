	@Test
	public void testPerformanceLogV2FetchParentOfShallowCommit()
			throws Exception {
		RevCommit commit0 = remote.commit().message("0").create();
		RevCommit commit1 = remote.commit().message("1").parent(commit0)
				.create();
		RevCommit tip = remote.commit().message("2").parent(commit1).create();
		remote.update("master"

		testProtocol = new TestProtocol<>((Object req
			UploadPack up = new UploadPack(db);
			up.setPerformanceLogHook(eventRecords -> {
				assertNotNull(eventRecords);
				assertTrue(eventRecords.get(0).getName()
						.equals("reachability-check"));
				assertTrue(eventRecords.get(1).getName().equals("negotiation"));
			});
			up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
			up.getRevWalk()
					.assumeShallow(Collections.singleton(commit1.getId()));
			return up;
		}
		uri = testProtocol.register(ctx

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit0.name())));
		}

	}

