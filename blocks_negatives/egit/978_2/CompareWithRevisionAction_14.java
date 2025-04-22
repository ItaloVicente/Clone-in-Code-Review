public class CompareWithRevisionAction extends TeamAction {

	@Override
	public void execute(IAction action) {
		IHistoryView view = TeamUI.showHistoryFor(TeamUIPlugin.getActivePage(), getSelectedResources()[0], null);
		if (view == null)
			return;
		IHistoryPage page = view.getHistoryPage();
		if (page instanceof GitHistoryPage){
			GitHistoryPage gitHistoryPage = (GitHistoryPage) page;
			gitHistoryPage.setCompareMode(true);
		}
	}

	public boolean isEnabled() {
		return !getSelection().isEmpty();
