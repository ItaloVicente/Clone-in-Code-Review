	private void progress(String pkt) throws IOException {
		pkt = progressBuffer + pkt;
		for (;;) {
			final int lf = pkt.indexOf('\n');
			final int cr = pkt.indexOf('\r');
			final int s;
			if (0 <= lf && 0 <= cr)
				s = Math.min(lf, cr);
			else if (0 <= lf)
				s = lf;
			else if (0 <= cr)
				s = cr;
			else
				break;

			doProgressLine(pkt.substring(0, s + 1));
			pkt = pkt.substring(s + 1);
