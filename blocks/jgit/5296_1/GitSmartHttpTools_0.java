	private static void sendInfoRefsError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pck = new PacketLineOut(buf);
		String svc = req.getParameter("service");
		pck.writeString("# service=" + svc + "\n");
		pck.end();
		pck.writeString("ERR " + textForGit);
		send(req
	}

	private static void sendUploadPackError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pckOut = new PacketLineOut(buf);

		boolean sideband;
		try {
			String line = new PacketLineIn(req.getInputStream()).readStringRaw();
			UploadPack.FirstLine parsed = new UploadPack.FirstLine(line);
			sideband = (parsed.getOptions().contains(OPTION_SIDE_BAND)
					|| parsed.getOptions().contains(OPTION_SIDE_BAND_64K));
		} catch (IOException e) {
			sideband = false;
		}

		if (sideband)
			writeSideBand(buf
		else
			writePacket(pckOut
		send(req
	}

	private static void sendReceivePackError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pckOut = new PacketLineOut(buf);

		boolean sideband;
		try {
			String line = new PacketLineIn(req.getInputStream()).readStringRaw();
			ReceivePack.FirstLine parsed = new ReceivePack.FirstLine(line);
			sideband = parsed.getCapabilities().contains(CAPABILITY_SIDE_BAND_64K);
		} catch (IOException e) {
			sideband = false;
		}

		if (sideband)
			writeSideBand(buf
		else
			writePacket(pckOut
		send(req
	}

	private static void writeSideBand(OutputStream out
			throws IOException {
		OutputStream msg = new SideBandOutputStream(CH_ERROR
		msg.write(Constants.encode("error: " + textForGit));
		msg.flush();
	}

	private static void writePacket(PacketLineOut pckOut
			throws IOException {
		pckOut.writeString("error: " + textForGit);
	}

