
	private void parsePack(ByteArrayInputStream recvStream) throws Exception {
		SideBandInputStream sb = new SideBandInputStream(
				recvStream
				new StringWriter()
		client.newObjectInserter().newPackParser(sb).parse(NullProgressMonitor.INSTANCE);
	}

	@Test
	public void testV2FetchRequestPolicyAdvertised() throws Exception {
		RevCommit advertized = remote.commit().message("x").create();
		RevCommit unadvertized = remote.commit().message("y").create();
		remote.update("branch1"

		uploadPackV2(
			RequestPolicy.ADVERTISED
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + advertized.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unadvertized.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.ADVERTISED
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unadvertized.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyReachableCommit() throws Exception {
		RevCommit reachable = remote.commit().message("x").create();
		RevCommit advertized = remote.commit().message("x").parent(reachable).create();
		RevCommit unreachable = remote.commit().message("y").create();
		remote.update("branch1"

		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + reachable.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyTip() throws Exception {
		RevCommit parentOfTip = remote.commit().message("x").create();
		RevCommit tip = remote.commit().message("y").parent(parentOfTip).create();
		remote.update("secret"

		uploadPackV2(
			RequestPolicy.TIP
			new RejectAllRefFilter()
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + tip.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + parentOfTip.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.TIP
			new RejectAllRefFilter()
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + parentOfTip.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyReachableCommitTip() throws Exception {
		RevCommit parentOfTip = remote.commit().message("x").create();
		RevCommit tip = remote.commit().message("y").parent(parentOfTip).create();
		RevCommit unreachable = remote.commit().message("y").create();
		remote.update("secret"

		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT_TIP
			new RejectAllRefFilter()
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + parentOfTip.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT_TIP
			new RejectAllRefFilter()
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyAny() throws Exception {
		RevCommit unreachable = remote.commit().message("y").create();

		uploadPackV2(
			RequestPolicy.ANY
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchServerDoesNotStopNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}

	@Test
	public void testV2FetchServerStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"have " + barParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"ACK " + fooParent.toObjectId().getName()
				"ACK " + barParent.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(fooParent.toObjectId()));
		assertTrue(client.hasObject(fooChild.toObjectId()));
		assertFalse(client.hasObject(barParent.toObjectId()));
		assertTrue(client.hasObject(barChild.toObjectId()));
	}

	@Test
	public void testV2FetchClientStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(fooParent.toObjectId()));
		assertTrue(client.hasObject(fooChild.toObjectId()));
		assertTrue(client.hasObject(barParent.toObjectId()));
		assertTrue(client.hasObject(barChild.toObjectId()));
	}

	private static class RejectAllRefFilter implements RefFilter {
		@Override
		public Map<String
			return new HashMap<String
		}
	};
