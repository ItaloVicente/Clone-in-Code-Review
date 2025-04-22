		else {
			detachingSymbolicLink = detach && ref.isSymbolic();
			if (detachingSymbolicLink)
				ref = new ObjectIdRef.Unpeeled(LOOSE
		}
		RefDirectoryUpdate refDirUpdate = new RefDirectoryUpdate(this
		if (detachingSymbolicLink)
			refDirUpdate.setDetachingSymbolicRef();
		return refDirUpdate;
