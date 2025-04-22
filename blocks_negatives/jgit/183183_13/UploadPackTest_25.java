		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"deepen-not base\n",
			"want " + child1.toObjectId().getName() + "\n",
			"want " + child2.toObjectId().getName() + "\n",
			"done\n",
