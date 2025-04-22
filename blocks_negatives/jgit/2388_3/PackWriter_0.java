	 * <p>
	 * When iterator returns object that has {@link RevFlag#UNINTERESTING} flag,
	 * this object won't be included in an output pack. Instead, it is recorded
	 * as edge-object (known to remote repository) for thin-pack. In such a case
	 * writer may pack objects with delta base object not within set of objects
	 * to pack, but belonging to party repository - those marked with
	 * {@link RevFlag#UNINTERESTING} flag. This type of pack is used only for
	 * transport.
	 * </p>
