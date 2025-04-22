
		ArrayList<String> lines = new ArrayList<>();
		String line;
		while ((line = pckIn.readString()) != PacketLineIn.END) {
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
