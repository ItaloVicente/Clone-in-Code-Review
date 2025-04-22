		if (totalWork == UNKNOWN) {
			m.append(cmp);
		} else {
			final int pcnt = (cmp * 100 / totalWork);
			if (pcnt < 100)
				m.append(' ');
			if (pcnt < 10)
				m.append(' ');
			m.append(pcnt);
			m.append("% (");
			m.append(cmp);
			m.append("/");
			m.append(totalWork);
			m.append(")");
		}
		if (eol != null)
			m.append(eol);
		else
			m.append("   \r");
		out.print(m);
		out.flush();
