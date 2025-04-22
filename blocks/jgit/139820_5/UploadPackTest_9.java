		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(RequestPolicy.REACHABLE_COMMIT
						"command=fetch\n"
						"want " + unreachable.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getCause().getMessage()
				containsString("want " + unreachable.name() + " not valid"));
