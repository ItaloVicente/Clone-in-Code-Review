		PackProtocolException e = assertThrows(PackProtocolException.class
				() -> uploadPackV2("command=fetch\n"
						"deepen-since 1510000\n"
						"want " + tooOld.toObjectId().getName() + "\n"
						"done\n"
		assertThat(e.getMessage()
				containsString("No commits selected for shallow request"));
