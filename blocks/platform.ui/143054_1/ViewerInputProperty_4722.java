public class ViewerInputProperty<S, T> extends ViewerValueProperty<S, T> {
	private final Object valueType;

	public ViewerInputProperty(Object valueType) {
		this.valueType = valueType;
	}

	public ViewerInputProperty() {
		this(null);
	}

