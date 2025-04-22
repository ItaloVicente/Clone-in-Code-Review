		List<RefSpec> specs = calculateRefSpecs(dst2);
		System.out.println("specs.size()='" + specs.size() + "'");
		if (specs.size() > 0) {
			System.out.println(specs.get(0));
		}
		if (specs.size() > 1) {
			System.out.println(specs.get(1));
		}
