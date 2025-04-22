		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						"command=ls-refs\n"
						PacketLineIn.DELIM
						"invalid-argument\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				is("unexpected invalid-argument"));
