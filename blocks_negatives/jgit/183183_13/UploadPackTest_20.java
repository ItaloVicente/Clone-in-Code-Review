		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + child.toObjectId().getName() + "\n",
			"deepen 1\n",
			"done\n",
				PacketLineIn.end());
