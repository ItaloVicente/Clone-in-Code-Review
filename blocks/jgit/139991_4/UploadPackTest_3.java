				() -> uploadPackV2(
						(UploadPack up) -> {
							up.setRequestPolicy(RequestPolicy.TIP);
							up.setRefFilter(new RejectAllRefFilter());
						}
						"command=fetch\n"
