public class RepositoryCommit extends WorkbenchAdapter implements IAdaptable {

	private static DateFormat FORMAT = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.SHORT);

	public static String formatDate(final Date date) {
		if (date == null)
			return ""; //$NON-NLS-1$
		synchronized (FORMAT) {
			return FORMAT.format(date);
		}
	}
