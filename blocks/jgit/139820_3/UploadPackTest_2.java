		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						RequestPolicy.TIP
						new RejectAllRefFilter()
						null
						"command=fetch\n"
						PacketLineIn.DELIM
						"want " + parentOfTip.name() + "\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				containsString("want " + parentOfTip.name() + " not valid"));
