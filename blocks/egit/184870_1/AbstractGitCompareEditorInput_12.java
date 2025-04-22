public abstract class AbstractGitCompareEditorInput extends CompareEditorInput {

	private static final Comparator<String> CMP = (left, right) -> {
		String l = left.startsWith("/") ? left.substring(1) : left; //$NON-NLS-1$
		String r = right.startsWith("/") ? right.substring(1) : right; //$NON-NLS-1$
		return l.replace('/', '\001')
				.compareToIgnoreCase(r.replace('/', '\001'));
	};
