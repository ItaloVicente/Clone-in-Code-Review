		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						"command=fetch\n"
						PacketLineIn.DELIM
						"deepen-since 1510000\n"
						"want " + tooOld.toObjectId().getName() + "\n"
						"done\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				is("No commits selected for shallow request"));
