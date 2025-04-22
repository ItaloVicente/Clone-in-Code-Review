				String actionToken = line.substring(0
				Action action = null;
				try {
					action = Action.parse(actionToken);
				} catch (Exception e) {
				}
				if (action != null)
