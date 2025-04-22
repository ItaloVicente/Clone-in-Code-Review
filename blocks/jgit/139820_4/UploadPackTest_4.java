		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						"command=fetch\n"
						PacketLineIn.DELIM
						"deepen-not four\n"
						"want " + two.toObjectId().getName() + "\n"
						"done\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				is("No commits selected for shallow request"));
