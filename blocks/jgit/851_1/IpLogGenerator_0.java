		try {
			String line;

			while ((line = br.readLine()) != null) {
				String[] field = line.trim().split(" *\\| *");
				String user = field[1];
				String name = field[2];
				String email = field[3];
				Date begin = parseDate(dt
				Date end = parseDate(dt

				if (user.startsWith("username:"))
					user = user.substring("username:".length());

				Committer who = committersById.get(user);
				if (who == null) {
					who = new Committer(user);
					int sp = name.indexOf(' ');
					if (0 < sp) {
						who.setFirstName(name.substring(0
						who.setLastName(name.substring(sp + 1).trim());
					} else {
						who.setFirstName(name);
						who.setLastName(null);
					}
					committersById.put(who.getID()
