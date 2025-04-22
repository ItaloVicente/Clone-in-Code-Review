		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(RequestPolicy.TIP
						null
						"want " + parentOfTip.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
				containsString("want " + parentOfTip.name() + " not valid"));
