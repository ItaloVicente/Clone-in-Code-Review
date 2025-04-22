		server.getConfig().setBoolean("uploadpack", null, "allowrefinwant", true);
		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want-ref refs/heads/one\n",
			"want-ref refs/heads/two\n",
			"done\n",
			PacketLineIn.end());
