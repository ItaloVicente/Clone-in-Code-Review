	private void format(StringBuilder s
			int totalWork
		s.append("\r");
		s.append(taskName);
		s.append(": ");
		while (s.length() < 25)
			s.append(' ');

		String endStr = String.valueOf(totalWork);
		String curStr = String.valueOf(cmp);
		while (curStr.length() < endStr.length())
			curStr = " " + curStr;
		if (pcnt < 100)
			s.append(' ');
		if (pcnt < 10)
			s.append(' ');
		s.append(pcnt);
		s.append("% (");
		s.append(curStr);
		s.append("/");
		s.append(endStr);
		s.append(")");
