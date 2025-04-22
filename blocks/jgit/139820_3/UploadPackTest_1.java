		UploadPackInternalServerErrorException e = assertThrows(
				UploadPackInternalServerErrorException.class
				() -> uploadPackV2(
						RequestPolicy.REACHABLE_COMMIT
						null
						null
						"command=fetch\n"
						PacketLineIn.DELIM
						"want " + unreachable.name() + "\n"
						PacketLineIn.END));
		assertThat(e.getCause().getMessage()
				containsString("want " + unreachable.name() + " not valid"));
