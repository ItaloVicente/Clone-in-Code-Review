	public boolean hasObject(ObjectToPack obj
		try {
			LocalObjectRepresentation local = (LocalObjectRepresentation) rep;
			for (PackFile pack : getPacks()) {
				if (local.pack == pack)
					return true;
