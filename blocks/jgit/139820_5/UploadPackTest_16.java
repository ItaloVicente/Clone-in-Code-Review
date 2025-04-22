		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
						"deepen-not four\n"
						"want " + two.toObjectId().getName() + "\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
				is("No commits selected for shallow request"));
