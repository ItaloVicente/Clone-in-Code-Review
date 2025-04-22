		protected Result applyPatchAllowConflicts()
				throws PatchApplyException
			InputStream patchStream = getTestResource(name + ".patch");
			if (inCore) {
				try (ObjectInserter oi = db.newObjectInserter()) {
					return new PatchApplier(db
				}
			}
			return new PatchApplier(db).allowConflicts().applyPatch(patchStream);
		}

