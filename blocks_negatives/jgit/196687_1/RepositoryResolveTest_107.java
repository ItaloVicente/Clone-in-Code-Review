	public void testDistance() throws IOException {
		assertEquals("49322bb17d3acc9146f98c97d078513228bbf3c0",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~0").name());
		assertEquals("6e1475206e57110fcef4b92320436c1e9872a322",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~1").name());
		assertEquals("1203b03dc816ccbb67773f28b3c19318654b0bc8",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~2").name());
		assertEquals("bab66b48f836ed950c99134ef666436fb07a09a0",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~3").name());
		assertEquals("bab66b48f836ed950c99134ef666436fb07a09a0",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~03").name());
		assertEquals("6e1475206e57110fcef4b92320436c1e9872a322",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~").name());
		assertEquals("1203b03dc816ccbb67773f28b3c19318654b0bc8",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~~").name());
		assertEquals("bab66b48f836ed950c99134ef666436fb07a09a0",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~~~").name());
		assertEquals("1203b03dc816ccbb67773f28b3c19318654b0bc8",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~~1").name());
		assertEquals("1203b03dc816ccbb67773f28b3c19318654b0bc8",db.resolve("49322bb17d3acc9146f98c97d078513228bbf3c0~~~0").name());
