			assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
					commands.get(0).getResult());
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(2)));
			assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
					.keySet().toString());
			assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
			assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
