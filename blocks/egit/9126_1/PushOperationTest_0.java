	@Test
	public void testUpdateTrackingBranchIfSpecifiedInRemoteRefUpdate() throws Exception {
		IProject project = importProject(repository2, projectName);
		RevCommit commit = repository2.addAndCommit(project, new File(workdir2, "test.txt"), "Commit in repository 2");
		project.delete(false, false, null);

		URIish remote = repository1.getUri();
		String trackingRef = "refs/remotes/origin/master";
		RemoteRefUpdate update = new RemoteRefUpdate(
				repository2.getRepository(), "HEAD", "refs/heads/master", false,
				trackingRef, null);
		PushOperationSpecification spec = new PushOperationSpecification();
		spec.addURIRefUpdates(remote, Arrays.asList(update));

		PushOperation push = new PushOperation(repository2.getRepository(),
				spec, false, 0);
		push.run(null);

		PushOperationResult result = push.getOperationResult();
		PushResult pushResult = result.getPushResult(remote);
		assertNotNull("Expected result to have tracking ref update", pushResult.getTrackingRefUpdate(trackingRef));

		ObjectId trackingId = repository2.getRepository().resolve(trackingRef);
		assertEquals("Expected tracking branch to be updated", commit.getId(), trackingId);
	}

