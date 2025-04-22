		protected Result applyPatch()
				throws PatchApplyException, PatchFormatException, IOException {
			InputStream patchStream = getTestResource(name + ".patch");
			if (inCore) {
				try (ObjectInserter oi = db.newObjectInserter()) {
					return new PatchApplier(db, baseTip, oi).applyPatch(patchStream);
