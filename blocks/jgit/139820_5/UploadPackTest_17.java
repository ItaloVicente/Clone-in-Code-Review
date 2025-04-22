		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
						"invalid-argument\n"
		assertThat(e.getCause().getMessage()
				is("unexpected invalid-argument"));
