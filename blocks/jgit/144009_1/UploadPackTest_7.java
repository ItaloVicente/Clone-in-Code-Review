		PackProtocolException e = assertThrows(PackProtocolException.class
				() -> uploadPackV2("command=fetch\n"
						"deepen-not four\n"
						"want " + two.toObjectId().getName() + "\n"
						PacketLineIn.end()));
		assertThat(e.getMessage()
				containsString("No commits selected for shallow request"));
