				assertTrue("merge -X " + contentStrategy.name(),
						new File(db.getWorkTree(), "a").exists());
				assertEquals("merge -X " + contentStrategy.name(),
						"1\na(side)\n3\n",
						read(new File(db.getWorkTree(), "a")));
				assertEquals("merge -X " + contentStrategy.name(), "1\nb\n3\n",
						read(new File(db.getWorkTree(), "b")));
