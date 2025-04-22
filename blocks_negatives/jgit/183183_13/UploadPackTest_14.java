		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + commit.toObjectId().getName() + "\n",
			"no-progress\n",
			"done\n",
				PacketLineIn.end());
