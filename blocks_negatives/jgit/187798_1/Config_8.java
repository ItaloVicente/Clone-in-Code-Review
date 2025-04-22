		final String s;

		if (value >= GiB && (value % GiB) == 0)
		else if (value >= MiB && (value % MiB) == 0)
		else if (value >= KiB && (value % KiB) == 0)
		else
			s = String.valueOf(value);

		setString(section, subsection, name, s);
