		else {
			detachingSymbolicRef = detach && ref.isSymbolic();
			if (detachingSymbolicRef)
				ref = new ObjectIdRef.Unpeeled(LOOSE
		}
		RefDirectoryUpdate refDirUpdate = new RefDirectoryUpdate(this
		if (detachingSymbolicRef)
			refDirUpdate.setDetachingSymbolicRef();
		return refDirUpdate;
