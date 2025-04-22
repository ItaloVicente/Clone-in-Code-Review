				final Repository db = getRepository(req);
				rsp.setContentType("application/x-" + svc + "-advertisement");

				final SmartOutputStream buf = new SmartOutputStream(req, rsp);
				final PacketLineOut out = new PacketLineOut(buf);
				out.writeString("# service=" + svc + "\n");
				out.end();
				advertise(req, db, new PacketLineOutRefAdvertiser(out));
				buf.close();
