public interface IDecoratableResource {

	/**
	 * Set of possible staging states for a resource
	 */
	public enum Staged {
		/** Represents a resource that is not staged */
		NOT_STAGED,
		/** Represents a resource that has been modified */
		MODIFIED,
		/** Represents a resource that is added to Git */
		ADDED,
		/** Represents a resource that is removed from Git */
		REMOVED,
		/** Represents a resource that has been renamed */
		RENAMED
	}
