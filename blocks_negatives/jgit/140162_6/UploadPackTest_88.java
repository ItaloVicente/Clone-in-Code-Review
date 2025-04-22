		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + child.toObjectId().getName() + "\n",
			"ofs-delta\n",
			"done\n",
			PacketLineIn.END);
