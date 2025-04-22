			List<Program> candidates = entry.getValue();
			boolean contains = candidates.contains(found.getProgram());
			if (contains && candidates.size() > 1) {
				candidates.contains(found.getProgram());
			}
			assertTrue("No matching external editor found for id: " + id + ", list: " + candidates + ", expected: "
					+ found + " / " + found.getProgram(), contains);
