		final byte[] raw;
		try {
			final Repository db = getRepository(req);
			final String name = req.getParameter("service");

			if (name != null && name.startsWith("git-")) {
				final ByteArrayOutputStream buf = new ByteArrayOutputStream();
				final PacketLineOut out = new PacketLineOut(buf);
				out.writeString("# service=" + name + "\n");

				if ("git-receive-pack".equals(name)) {
					final ReceivePack rp = receivePackFactory.create(req
					rp.sendAdvertisedRefs(new PacketLineOutRefAdvertiser(out));

				} else {
					throw new ServiceNotEnabledException();
				}

				raw = buf.toByteArray();
				rsp.setContentType("application/x-" + name + "-advertisement");

			} else {
				raw = dumbHttp(db);
				rsp.setContentType("text/plain");
				rsp.setCharacterEncoding(Constants.CHARACTER_ENCODING);
			}

		} catch (ServiceNotEnabledException e) {
			rsp.reset();
			rsp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

