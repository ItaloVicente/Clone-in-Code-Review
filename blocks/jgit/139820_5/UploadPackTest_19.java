		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
						"want " + commit.toObjectId().getName() + "\n"
						"filter blob:limit=5\n"
		assertThat(e.getCause().getMessage()
				is("unexpected filter blob:limit=5"));
