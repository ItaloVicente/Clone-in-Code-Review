		String line;

		while ((line = br.readLine()) != null) {
			String[] field = line.trim().split(" *\\| *");
			String user = field[1];
			String name = field[2];
			String email = field[3];
			Date begin = parseDate(dt, field[4]);
			Date end = parseDate(dt, field[5]);

			if (user.startsWith("username:"))
				user = user.substring("username:".length());

			Committer who = committersById.get(user);
			if (who == null) {
				who = new Committer(user);
				int sp = name.indexOf(' ');
				if (0 < sp) {
					who.setFirstName(name.substring(0, sp).trim());
					who.setLastName(name.substring(sp + 1).trim());
				} else {
					who.setFirstName(name);
					who.setLastName(null);
