		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class,
				() -> uploadPackV2("command=fetch\n", PacketLineIn.delimiter(),
						"want-ref refs/heads/one\n", "done\n",
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage(),
				containsString("unexpected want-ref refs/heads/one"));
