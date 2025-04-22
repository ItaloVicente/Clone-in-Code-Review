 * In every drag and drop operation there is a <code>DragSource</code> and
 * a <code>DropTarget</code>.  When a drag occurs a <code>Transfer</code> is
 * used to marshall the drag data from the source into a byte array.  If a drop
 * occurs another <code>Transfer</code> is used to marshall the byte array into
 * drop data for the target.
 * </p><p>
 * This class can be used for a <code>Viewer<code> or an SWT component directly.
 * A singleton is provided which may be serially reused (see <code>getInstance</code>).
 * It is not intended to be subclassed.
