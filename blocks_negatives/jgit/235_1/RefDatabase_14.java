	RefRename newRename(String fromRef, String toRef) throws IOException {
		refreshPackedRefs();
		Ref f = readRefBasic(fromRef, 0);
		Ref t = new Ref(Ref.Storage.NEW, toRef, null);
		RefUpdate refUpdateFrom = new RefUpdate(this, f, fileForRef(f.getName()));
		RefUpdate refUpdateTo = new RefUpdate(this, t, fileForRef(t.getName()));
		return new RefRename(refUpdateTo, refUpdateFrom);
	}
