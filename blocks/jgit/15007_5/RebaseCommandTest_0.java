				int firstBlank = line.indexOf(' ');
				if (firstBlank != -1) {
					String actionToken = line.substring(0
					Action action = null;
					try {
						action = Action.parse(actionToken);
					} catch (Exception e) {
					}
					if (Action.PICK.equals(action))
						count++;
