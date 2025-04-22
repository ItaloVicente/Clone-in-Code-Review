		uploadPackV2(
			(UploadPack up) -> {up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);}
			"command=fetch\n"
			PacketLineIn.delimiter()
			"want " + reachable.name() + "\n"
				PacketLineIn.end());
