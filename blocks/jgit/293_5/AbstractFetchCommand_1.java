
		showRemoteMessages(r.getMessages());
	}

	static void showRemoteMessages(String pkt) {
		while (0 < pkt.length()) {
			final int lf = pkt.indexOf('\n');
			final int cr = pkt.indexOf('\r');
			final int s;
			if (0 <= lf && 0 <= cr)
				s = Math.min(lf
			else if (0 <= lf)
				s = lf;
			else if (0 <= cr)
				s = cr;
			else {
				System.err.println("remote: " + pkt);
				break;
			}

			if (pkt.charAt(s) == '\r')
				System.err.print("remote: " + pkt.substring(0
			else
				System.err.println("remote: " + pkt.substring(0

			pkt = pkt.substring(s + 1);
		}
		System.err.flush();
