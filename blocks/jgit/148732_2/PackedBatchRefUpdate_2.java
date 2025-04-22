			switch (c.getType()) {
			case DELETE:
				delta--;
				break;
			case CREATE:
				delta++;
				break;
			default:
