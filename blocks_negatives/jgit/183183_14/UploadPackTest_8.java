		uploadPackV2(
				(UploadPack up) -> {
					up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT_TIP);
					up.setRefFilter(new RejectAllRefFilter());
				},
				"command=fetch\n",
				PacketLineIn.delimiter(), "want " + parentOfTip.name() + "\n",
				PacketLineIn.end());
