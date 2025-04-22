		uploadPackV2(
			(UploadPack up) -> {up.setRequestPolicy(RequestPolicy.ADVERTISED);}
			"command=fetch\n"
			PacketLineIn.delimiter()
			"want " + advertized.name() + "\n"
			PacketLineIn.end());
