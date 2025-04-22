/**
 * Base class for a set of object loader classes for packed objects.
 */
abstract class PackedObjectLoader extends ObjectLoader {
	protected final PackFile pack;

	/** Position of the first byte of the object's header. */
	protected final long objectOffset;

	/** Bytes used to express the object header, including delta reference. */
	protected final int headerSize;
