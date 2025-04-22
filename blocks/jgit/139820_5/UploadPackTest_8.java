		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(RequestPolicy.ADVERTISED
						"command=fetch\n"
						"want " + unadvertized.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
				containsString("want " + unadvertized.name() + " not valid"));
