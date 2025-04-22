	@Test
	public void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck()
			throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				newCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = refdir.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo) {
			@Override
			public boolean isMergedInto(RevCommit base
				throw new AssertionError("isMergedInto() should not be called");
			}
		}
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(A.getId()
	}

