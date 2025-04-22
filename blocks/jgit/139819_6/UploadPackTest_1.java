		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
						"want-ref refs/heads/one\n"
						"want-ref refs/heads/nonExistentRef\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
