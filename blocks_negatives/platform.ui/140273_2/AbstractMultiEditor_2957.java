	 * implementation simply forwards the change to listeners on this multi
	 * editor by calling <code>firePropertyChange</code> with the same property
	 * id. For example, if the dirty state of a nested editor changes (property
	 * id <code>ISaveablePart.PROP_DIRTY</code>), this method handles it by
	 * firing a property change event for <code>ISaveablePart.PROP_DIRTY</code>
	 * to property listeners on this multi editor.
