			assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
			assertEquals(ReceiveCommand.Result.LOCK_FAILURE, commands.get(1)
					.getResult());
			assertEquals(ReceiveCommand.Result.LOCK_FAILURE, commands.get(2)
					.getResult());
			assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
					.keySet().toString());
			assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
			assertEquals(B.getId(), refs.get("refs/heads/masters").getObjectId());
