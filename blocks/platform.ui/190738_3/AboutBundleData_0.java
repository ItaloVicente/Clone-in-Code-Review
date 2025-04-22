
	public interface ExtendedSigningInfo {

		boolean isSigned(Bundle bundle);

		String getSigningType(Bundle bundle);

		Date getSigningTime(Bundle bundle);

		String getSigningDetails(Bundle bundle);
	}
