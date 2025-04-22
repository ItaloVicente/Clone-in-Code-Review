			final String twstr = String.valueOf(totalWork);
			String cmpstr = String.valueOf(cmp);
			while (cmpstr.length() < twstr.length())
				cmpstr = " " + cmpstr; //$NON-NLS-1$
			final int pcnt = (cmp * 100 / totalWork);
			if (pcnt < 100)
				m.append(' ');
			if (pcnt < 10)
				m.append(' ');
			m.append(pcnt);
			m.append("% ("); //$NON-NLS-1$
			m.append(cmpstr);
			m.append("/"); //$NON-NLS-1$
			m.append(twstr);
			m.append(")"); //$NON-NLS-1$

