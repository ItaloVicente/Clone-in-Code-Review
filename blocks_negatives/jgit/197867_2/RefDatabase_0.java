	 * The default implementation is not efficient. Implementors of {@link RefDatabase}
	 * should override this method directly if a better implementation is possible.
	 * 
	 * @param include string that names of refs should start with; may be empty.
	 * @param excludes strings that names of refs can't start with; may be empty.
	 * @return immutable list of refs whose names start with {@code prefix} and none
	 *         of the strings in {@code exclude}.
	 * @throws java.io.IOException the reference space cannot be accessed.
