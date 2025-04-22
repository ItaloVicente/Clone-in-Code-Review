	@Test
	@SuppressWarnings("boxing")
	public void shouldReturnFalseWhenRemoteDoesNotExist2() throws Exception{
		GitResourceVariantComparator grvc = new GitResourceVariantComparator(
				null);

		IResource local = createMock(IResource.class);
		expect(local.exists()).andReturn(false);
		replay(local);
		IResourceVariant remote = new GitFolderResourceVariant(repo,
				ObjectId.zeroId(), "./");

		assertFalse(grvc.compare(local, remote));
		verify(local);
	}

