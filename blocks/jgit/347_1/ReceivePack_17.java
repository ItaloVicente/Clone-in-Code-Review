		if (refs == null) {
			if (advertiseError == null)
				advertiseError = new StringBuilder();
			advertiseError.append(what).append('\n');
		} else {
			try {
				if (msgs != null)
					msgs.write("error: " + what + "\n");
			} catch (IOException e) {
			}
		}
