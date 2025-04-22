		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + child.toObjectId().getName() + "\n",
			"ofs-delta\n",
			"done\n",
				PacketLineIn.end());
