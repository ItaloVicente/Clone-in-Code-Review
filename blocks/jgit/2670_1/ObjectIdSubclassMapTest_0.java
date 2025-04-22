
		m.clear();
		HashSet<ObjectId> exp = new HashSet<ObjectId>();
		for (int id = 32; id < 8000; id++) {
			SubId s = new SubId(id(id));
			m.add(s);
			exp.add(s);
		}

		i = m.iterator();
		while (i.hasNext()) {
			SubId s = i.next();
			assertTrue(exp.remove(s));
		}
		assertTrue(exp.isEmpty());
