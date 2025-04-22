	public static <S, T> IWidgetValueProperty<S, T> selection() {
		return new WidgetSelectionProperty<>();
	}

	public static IWidgetValueProperty<DateTime, Date> dateTimeSelection() {
		return new DateTimeSelectionProperty();
	}

	public static IWidgetValueProperty<Button, Boolean> buttonSelection() {
		return new ButtonSelectionProperty();
	}

	public static IWidgetValueProperty<Combo, String> comboSelection() {
		return new ComboSelectionProperty();
	}

	public static IWidgetValueProperty<CCombo, String> ccomboSelection() {
		return new CComboSelectionProperty();
	}

	public static IWidgetValueProperty<List, String> listSelection() {
		return new ListSelectionProperty();
	}

	public static IWidgetValueProperty<MenuItem, Boolean> menuItemSelection() {
		return new MenuItemSelectionProperty();
	}

	public static IWidgetValueProperty<Scale, Integer> scaleSelection() {
		return new ScaleSelectionProperty();
	}

	public static IWidgetValueProperty<Slider, Integer> sliderSelection() {
		return new SliderSelectionProperty();
	}

	public static IWidgetValueProperty<Spinner, Integer> spinnerSelection() {
		return new SpinnerSelectionProperty();
