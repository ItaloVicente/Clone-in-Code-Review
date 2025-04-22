			if (!active) {
				if (b.getState() != Bundle.ACTIVE) {
					System.out.println(b.getSymbolicName());
				}
			} else {
				if (b.getState() == Bundle.ACTIVE) {
					System.out.println(b.getSymbolicName());
				}

