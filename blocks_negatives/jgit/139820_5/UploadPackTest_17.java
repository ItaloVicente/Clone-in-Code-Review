		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + child.toObjectId().getName() + "\n",
			"have " + parent.toObjectId().getName() + "\n",
			"thin-pack\n",
			"done\n",
				PacketLineIn.end());
