	 * Converts the provided <code>fromObject</code> to the requested
	 * {@link #getToType() to type}.
	 *
	 * @see org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.Object)
	 * @throws IllegalArgumentException
	 *             if the value isn't in the format required by the NumberFormat
	 *             or the value is out of range for the {@link #getToType() to
	 *             type}.
	 * @throws IllegalArgumentException
	 *             if conversion was not possible
	 * @since 1.7
