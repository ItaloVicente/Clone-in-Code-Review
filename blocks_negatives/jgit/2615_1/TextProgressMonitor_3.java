		if (totalWork == UNKNOWN) {
			m.append(cmp);
		} else {
			final String twstr = String.valueOf(totalWork);
			String cmpstr = String.valueOf(cmp);
			while (cmpstr.length() < twstr.length())
				cmpstr = " " + cmpstr;
			final int pcnt = (cmp * 100 / totalWork);
			if (pcnt < 100)
				m.append(' ');
			if (pcnt < 10)
				m.append(' ');
			m.append(pcnt);
			m.append("% (");
			m.append(cmpstr);
			m.append("/");
			m.append(twstr);
			m.append(")");
		}
