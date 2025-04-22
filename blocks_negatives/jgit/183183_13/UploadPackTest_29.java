		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want-ref refs/heads/one\n",
			"want " + two.toObjectId().getName() + "\n",
			"done\n",
