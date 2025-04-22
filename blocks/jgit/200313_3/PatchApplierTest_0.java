		protected Result applyPatch() throws IOException {
			try (InputStream patchStream = getTestResource(name + ".patch")) {
				Patch patch = new Patch();
				patch.parse(patchStream);
				if (inCore) {
					try (ObjectInserter oi = db.newObjectInserter()) {
						return new PatchApplier(db
					}
