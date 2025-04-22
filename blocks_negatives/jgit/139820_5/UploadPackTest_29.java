		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"deepen-not twotag\n",
			"want " + four.toObjectId().getName() + "\n",
			"done\n",
