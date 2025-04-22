public class WorkbookEditorsHandler extends FilteredTableBaseHandler {

	private static final String ORG_ECLIPSE_UI_WINDOW_OPEN_EDITOR_DROP_DOWN = "org.eclipse.ui.window.openEditorDropDown"; //$NON-NLS-1$

	@Override
	protected Object getInput(WorkbenchPage page) {
		List<EditorReference> refs = page.getSortedEditorReferences();
		return refs;
	}

	@Override
	protected boolean isFiltered() {
		return true;
	}

	private SearchPattern searchPattern;

	SearchPattern getMatcher() {
		return searchPattern;
	}

	@Override
	protected void setMatcherString(String pattern) {
		if (pattern.length() == 0) {
			searchPattern = null;
		} else {
			SearchPattern patternMatcher = new SearchPattern();
			patternMatcher.setPattern("*" + pattern); //$NON-NLS-1$
			searchPattern = patternMatcher;
		}
	}
