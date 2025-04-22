		try {
			wantReadAhead = true;
			for (DfsObjectRepresentation r : all) {
				r.pack.representation(this, r);
				packer.select(r.object, r);
				if ((++updated & 1) == 1 && posted < objectCount) {
					monitor.update(1);
					posted++;
				}
