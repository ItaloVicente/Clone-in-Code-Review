 * On <code>dragEnter</code>, <code>dragOperationChanged</code>, <code>dragOver</code>
 * and <code>drop</code>, a <i>current</i> listener is obtained from the set of all
 * <code>TransferDropTargetListeners</code>. The current listener is the first listener
 * to return <code>true</code> for
 * {@link TransferDropTargetListener#isEnabled(DropTargetEvent)}.
 * The current listener is forwarded all <code>DropTargetEvents</code> until some other
