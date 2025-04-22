 * This interface is typically included in interfaces where
 * persistance is required.
 * </p><p>
 * When the workbench is shutdown objects which implement this interface
 * will be persisted.  At this time the <code>getFactoryId</code> method
 * is invoked to discover the id of the element factory that will be used
 * to re-create the object from a memento.  Then the <code>saveState</code>
 * method is invoked to store the element data into a newly created memento.
 * The resulting mementos are collected up and written out to a single file.
