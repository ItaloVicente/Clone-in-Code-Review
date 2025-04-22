		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=ls-refs\n"
						PacketLineIn.delimiter()
						"invalid-argument\n"
		assertThat(e.getCause().getMessage()
				is("unexpected invalid-argument"));
