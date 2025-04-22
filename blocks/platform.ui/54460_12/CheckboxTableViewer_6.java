public class CheckboxTableViewer<E, I> extends TableViewer<E, I> implements ICheckable<E> {

	private ListenerList checkStateListeners = new ListenerList();

	private ICheckStateProvider checkStateProvider;

	@Deprecated
