public final class WidgetSingleSelectionIndexProperty<S extends Widget>
		extends WidgetDelegatingValueProperty<S, Integer> {
	private IValueProperty<S, Integer> cCombo;
	private IValueProperty<S, Integer> combo;
	private IValueProperty<S, Integer> list;
	private IValueProperty<S, Integer> table;
