public class WidgetEnabledProperty<S> extends WidgetDelegatingValueProperty<S, Boolean> {
	IValueProperty<S, Boolean> control;
	IValueProperty<S, Boolean> menu;
	IValueProperty<S, Boolean> menuItem;
	IValueProperty<S, Boolean> scrollBar;
	IValueProperty<S, Boolean> toolItem;
