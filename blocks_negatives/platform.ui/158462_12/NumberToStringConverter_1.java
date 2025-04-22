	 * Converts the provided <code>fromObject</code> to a <code>String</code>.
	 * If the converter was constructed for an object type, non primitive, a
	 * <code>fromObject</code> of <code>null</code> will be converted to an
	 * empty string.
	 *
	 * @param fromObject
	 *            value to convert. May be <code>null</code> if the converter
	 *            was constructed for a non primitive type.
	 * @see org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.Object)
	 * @since 1.7
