		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(RequestPolicy.REACHABLE_COMMIT_TIP
						new RejectAllRefFilter()
						PacketLineIn.delimiter()
						"want " + unreachable.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
				containsString("want " + unreachable.name() + " not valid"));
