			int i = 0;
			int index = msg.indexOf("{}");
			while (index >= 0) {
				msg = msg.replaceFirst("\\{\\}"
				index = msg.indexOf("{}");
			}
