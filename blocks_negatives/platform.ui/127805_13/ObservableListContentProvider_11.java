public class ObservableListContentProvider implements
		IStructuredContentProvider {
	private ObservableCollectionContentProvider impl;

	private static class Impl extends ObservableCollectionContentProvider
			implements IListChangeListener {
