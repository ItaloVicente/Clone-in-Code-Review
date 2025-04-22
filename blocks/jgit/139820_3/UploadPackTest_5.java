		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						"command=fetch\n"
						PacketLineIn.DELIM
						"invalid-argument\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				is("unexpected invalid-argument"));
