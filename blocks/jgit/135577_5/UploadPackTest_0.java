
		ArrayList<String> lines = new ArrayList<>();
		String line;
		while (!PacketLineIn.isEnd((line = pckIn.readString()))) {
			if (line.startsWith("fetch=")) {
				assertThat(
					Arrays.asList(line.substring(6).split(" "))
					containsInAnyOrder(fetchCapability
				lines.add("fetch");
			} else {
				lines.add(line);
			}
		}
		assertThat(lines
