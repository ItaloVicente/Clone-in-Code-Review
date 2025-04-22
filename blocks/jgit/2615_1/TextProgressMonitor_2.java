	private void format(StringBuilder s
		s.append("\r");
		s.append(taskName);
		s.append(": ");
		while (s.length() < 25)
			s.append(' ');
		s.append(workCurr);
	}
