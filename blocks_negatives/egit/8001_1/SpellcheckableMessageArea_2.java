	 * @param lineDelimiter
	 *            line delimiter used in text and for wrapping
	 * @return a list of {@link WrapEdit} objects which specify how the text
	 *         should be edited to obtain the wrapped text. Offsets of later
	 *         edits are already adjusted for the fact that wrapping a line may
	 *         shift the text backwards. So the list can just be iterated and
	 *         each edit applied in order.
