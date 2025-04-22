		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + commit.toObjectId().getName() + "\n",
			"filter blob:limit=5\n",
			"done\n",
				PacketLineIn.end());
