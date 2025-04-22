	 *
	 * @return if there's a tag that points to the commit being described, this
	 *         tag name is returned. Otherwise additional suffix is added to the
	 *         nearest tag, just like git-describe(1).
	 *         <p>
	 *         If none of the ancestors of the commit being described has any
	 *         tags at all, then this method returns null, indicating that
	 *         there's no way to describe this tag.
