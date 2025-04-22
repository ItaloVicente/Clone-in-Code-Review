	@Test
	public void testV2FetchShallowSince_noCommitsSelected() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit tooOld = remote.commit()
			.committer(new PersonIdent(person

		remote.update("branch1"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-since 1510000\n"
			"want " + tooOld.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
	}

