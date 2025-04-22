			String name = refName;
			if (name.startsWith("refs/heads/"))
				name = name.substring(11);
			if (name.startsWith("refs/remotes/"))
				name = name.substring(13);
			String message = "reset --" + type.toString().toLowerCase() + " " + name;
			ru.setRefLogMessage(message, false);
