		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
						"want-ref refs/heads/one\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
