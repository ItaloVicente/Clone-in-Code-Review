public class WidgetTooltipTextProperty<S extends Widget> extends WidgetDelegatingValueProperty<S, String> {
	private IValueProperty<S, String> cTabItem;
	private IValueProperty<S, String> control;
	private IValueProperty<S, String> tabItem;
	private IValueProperty<S, String> tableColumn;
	private IValueProperty<S, String> toolItem;
	private IValueProperty<S, String> trayItem;
	private IValueProperty<S, String> treeColumn;
