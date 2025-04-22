public class WidgetEnabledProperty<S extends Widget> extends WidgetDelegatingValueProperty<S, Boolean> {
	IValueProperty<Control, Boolean> control;
	IValueProperty<Menu, Boolean> menu;
	IValueProperty<MenuItem, Boolean> menuItem;
	IValueProperty<ScrollBar, Boolean> scrollBar;
	IValueProperty<ToolItem, Boolean> toolItem;
