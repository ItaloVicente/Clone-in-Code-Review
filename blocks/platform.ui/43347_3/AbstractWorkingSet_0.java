<<<<<<< HEAD
=======
	@Override
	protected AbstractWorkingSet clone() {
		try {
			AbstractWorkingSet clone = (AbstractWorkingSet) super.clone();
			clone.disconnect();
			return clone;
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}

>>>>>>> 6c67a76... Bug 37183: need "oldValue" for IWorkingSet PropertyChangeEvent
