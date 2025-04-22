		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + commit.toObjectId().getName() + "\n",
			"include-tag\n",
			"done\n",
				PacketLineIn.end());
