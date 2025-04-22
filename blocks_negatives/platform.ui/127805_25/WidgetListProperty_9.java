public abstract class WidgetListProperty extends SimpleListProperty implements
		IWidgetListProperty {
	@Override
	public IObservableList observe(Object source) {
		if (source instanceof Widget) {
			return observe((Widget) source);
		}
		return super.observe(source);
	}

