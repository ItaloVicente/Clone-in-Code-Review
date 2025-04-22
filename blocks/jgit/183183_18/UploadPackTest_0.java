
	@Test
	public void testObjectInfo() throws Exception {
		server.getConfig().setBoolean("uploadpack"
				true);

		RevBlob blob1 = remote.blob("foobar");
		RevBlob blob2 = remote.blob("fooba");
		RevTree tree = remote.tree(remote.file("1"
				remote.file("2"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		TestV2Hook hook = new TestV2Hook();
		ByteArrayInputStream recvStream = uploadPackV2((UploadPack up) -> {
			up.setProtocolV2Hook(hook);
		}
				"oid " + ObjectId.toString(blob1.getId())
				"oid " + ObjectId.toString(blob2.getId())
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(hook.objectInfoRequest
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is(ObjectId.toString(blob1.getId()) + " 6"));
		assertThat(pckIn.readString()
				is(ObjectId.toString(blob2.getId()) + " 5"));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));

		assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2((UploadPack up) -> {
					up.setProtocolV2Hook(hook);
				}
						PacketLineIn.end()));
	}
