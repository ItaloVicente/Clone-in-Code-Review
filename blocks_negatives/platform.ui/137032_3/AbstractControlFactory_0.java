public abstract class ControlFactory<F extends ControlFactory<?, ?>, C extends Control> {
	private Class<F> factoryClass;

	private Function<Composite, C> controlCreator;

	private List<Property<C>> properties = new ArrayList<>();
