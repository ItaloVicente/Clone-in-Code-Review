	 * @param context
	 *            the undo context of operations in question.
	 * @param part
	 *            the editor part that is displaying the element
	 * @param affectedObjects
	 *            the objects that are affected by the editor and considered to
	 *            be objects local to the editor. The objects are typically
	 *            instances of the preferredComparisonClass or else provide
	 *            adapters for the preferredComparisonClass, although this is
	 *            not required.
	 * @param preferredComparisonClass
	 *            the preferred class to be used when comparing the editor's
	 *            affectedObjects with those provided by the undoable operation
	 *            using
	 *            {@link org.eclipse.core.commands.operations.IAdvancedUndoableOperation#getAffectedObjects()}.
	 *            If the operation's affected objects are not instances of the
	 *            specified class, but are instances of
	 *            {@link org.eclipse.core.runtime.IAdaptable}, then an adapter
	 *            for this class will be requested. The preferredComparisonClass
	 *            may be <code>null</code>, which indicates that there is no
	 *            expected class or adapter necessary for the comparison.
