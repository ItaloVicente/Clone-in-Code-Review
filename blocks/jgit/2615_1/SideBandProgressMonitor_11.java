	private void format(StringBuilder s
			int totalWork
		s.append(taskName);
		s.append(": ");
		if (pcnt < 100)
			s.append(' ');
		if (pcnt < 10)
			s.append(' ');
		s.append(pcnt);
		s.append("% (");
		s.append(cmp);
		s.append("/");
		s.append(totalWork);
		s.append(")");
