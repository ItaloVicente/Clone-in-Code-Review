		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						"command=fetch\n"
						PacketLineIn.DELIM
						"want " + commit.toObjectId().getName() + "\n"
						"filter blob:limit=5\n"
						"done\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				is("unexpected filter blob:limit=5"));
