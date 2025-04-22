			if (id.equals("A")) {
				return Arrays.asList("C", "D");
			} else if (id.equals("B")) {
				return Collections.singleton("A");
			} else if (id.equals("C")) {
				return Collections.singleton("B");
			} else if (id.equals("D")) {
				return null;
			}
