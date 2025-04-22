		PackProtocolException e = assertThrows(PackProtocolException.class
				() -> uploadPackV2("command=fetch\n"
						"want " + commit.toObjectId().getName() + "\n"
						"filter blob:limit=5\n"
		assertThat(e.getMessage()
				containsString("unexpected filter blob:limit=5"));
