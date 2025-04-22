public class BindingTestSuite extends TestSuite {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new BindingTestSetup(new BindingTestSuite());
	}

	public BindingTestSuite() {

		addTestSuite(AggregateValidationStatusTest.class);
		addTestSuite(BindingTest.class);
		addTestSuite(DatabindingContextTest.class);
		addTestSuite(ListBindingTest.class);
		addTestSuite(UpdateStrategyTest.class);
		addTestSuite(UpdateListStrategyTest.class);
		addTestSuite(UpdateSetStrategyTest.class);
		addTestSuite(UpdateValueStrategyTest.class);
		addTestSuite(ValueBindingTest.class);
		addTestSuite(ObservablesManagerTest.class);
		addTestSuite(SideEffectTest.class);

		addTestSuite(PolicyTest.class);

		addTestSuite(AnonymousBeanValuePropertyTest.class);
		addTestSuite(AnonymousPojoValuePropertyTest.class);
		addTestSuite(BeanPropertiesTest.class);
		addTestSuite(BeansObservablesTest.class);
		addTestSuite(PojoObservablesTest.class);
		addTestSuite(PojoPropertiesTest.class);
		addTestSuite(SetOnlyJavaBeanTest.class);

		addTestSuite(NumberToStringConverterTest.class);
		addTestSuite(StringToNumberConverterTest.class);

		addTest(AbstractObservableTest.suite());
		addTestSuite(ChangeSupportTest.class);
		addTestSuite(DecoratingObservableTest.class);
		addTestSuite(Diffs_ListDiffTests.class);
		addTestSuite(DiffsTest.class);
		addTestSuite(ObservablesTest.class);
		addTestSuite(ObservableTrackerTest.class);
		addTestSuite(RealmTest.class);

		addTest(AbstractObservableListTest.suite());
		addTest(ComputedListTest.suite());
		addTest(DecoratingObservableListTest.suite());
		addTestSuite(ListDiffTest.class);
		addTestSuite(ListDiffVisitorTest.class);
		addTest(MultiListTest.suite());
		addTest(ObservableListTest.suite());
		addTest(WritableListTest.suite());

		addTestSuite(AbstractObservableMapTest.class);
		addTestSuite(BidiObservableMapTest.class);
		addTestSuite(ObservableMapTest.class);
		addTestSuite(WritableMapTest.class);
		addTestSuite(CompositeMapTest.class);
		addTestSuite(ComputedObservableMapTest.class);

		addTest(AbstractObservableSetTest.suite());
		addTest(ComputedSetTest.suite());
		addTest(DecoratingObservableSetTest.suite());
		addTest(ObservableSetTest.suite());
		addTest(UnionSetTest.suite());
		addTest(WritableSetTest.suite());

		addTestSuite(AbstractObservableValueTest.class);
		addTestSuite(AbstractVetoableValueTest.class);
		addTestSuite(ComputedValueTest.class);
		addTestSuite(DateAndTimeObservableValueTest.class);
		addTest(DecoratingObservableValueTest.suite());
		addTestSuite(DuplexingObservableValueTest.class);
		addTest(SelectObservableValueTest.suite());
		addTest(WritableValueTest.suite());

		addTestSuite(MultiValidatorTest.class);
		addTestSuite(ValidationStatusTest.class);

		addTestSuite(BindingMessagesTest.class);
		addTestSuite(BindingStatusTest.class);
		addTestSuite(ConverterValuePropertyTest.class);
		addTestSuite(DifferentRealmsBindingTest.class);
		addTestSuite(IdentityMapTest.class);
		addTestSuite(IdentitySetTest.class);
		addTestSuite(QueueTest.class);

		addTestSuite(DateConversionSupportTest.class);
		addTestSuite(IdentityConverterTest.class);
		addTestSuite(IntegerToStringConverterTest.class);
		addTestSuite(NumberToBigDecimalTest.class);
		addTestSuite(NumberToBigIntegerConverterTest.class);
		addTestSuite(NumberToByteConverterTest.class);
		addTestSuite(NumberToDoubleConverterTest.class);
		addTestSuite(NumberToFloatConverterTest.class);
		addTestSuite(NumberToIntegerConverterTest.class);
		addTestSuite(NumberToLongConverterTest.class);
		addTestSuite(NumberToShortConverterTest.class);
		addTestSuite(ObjectToPrimitiveValidatorTest.class);
		addTestSuite(StatusToStringConverterTest.class);
		addTestSuite(StringToBooleanConverterTest.class);
		addTestSuite(StringToByteConverterTest.class);
		addTestSuite(StringToCharacterConverterTest.class);
		addTestSuite(StringToNumberParserByteTest.class);
		addTestSuite(StringToNumberParserDoubleTest.class);
		addTestSuite(StringToNumberParserFloatTest.class);
		addTestSuite(StringToNumberParserIntegerTest.class);
		addTestSuite(StringToNumberParserLongTest.class);
		addTestSuite(StringToNumberParserShortTest.class);
		addTestSuite(StringToNumberParserTest.class);
		addTestSuite(StringToShortConverterTest.class);

		addTest(BeanObservableListDecoratorTest.suite());
		addTestSuite(BeanObservableSetDecoratorTest.class);
		addTestSuite(BeanObservableValueDecoratorTest.class);
		addTestSuite(BeanObservableListDecoratorTest.class);
		addTestSuite(BeanValuePropertyTest.class);
		addTest(JavaBeanObservableArrayBasedListTest.suite());
		addTest(JavaBeanObservableArrayBasedSetTest.suite());
		addTest(JavaBeanObservableListTest.suite());
		addTest(JavaBeanObservableMapTest.suite());
		addTest(JavaBeanObservableSetTest.suite());
		addTest(JavaBeanObservableValueTest.suite());
		addTestSuite(JavaBeanPropertyObservableMapTest.class);
		addTestSuite(BeanPropertyHelperTest.class);
		addTestSuite(BeanPropertyListenerSupportTest.class);
		addTestSuite(BeanPropertyListenerTest.class);

		addTest(ConstantObservableValueTest.suite());
		addTest(DelayedObservableValueTest.suite());
		addTest(EmptyObservableListTest.suite());
		addTest(EmptyObservableSetTest.suite());
		addTest(IdentityObservableSetTest.suite());
		addTest(MapEntryObservableValueTest.suite());
		addTest(StalenessObservableValueTest.suite());
		addTest(UnmodifiableObservableValueTest.suite());
		addTest(UnmodifiableObservableListTest.suite());
		addTest(UnmodifiableObservableSetTest.suite());
		addTest(ValidatedObservableValueTest.suite());
		addTest(ValidatedObservableListTest.suite());
		addTest(ValidatedObservableSetTest.suite());

		addTest(DetailObservableListTest.suite());
		addTestSuite(DetailObservableMapTest.class);
		addTest(DetailObservableSetTest.suite());
		addTest(DetailObservableValueTest.suite());
		addTest(ListDetailValueObservableListTest.suite());
		addTest(MapDetailValueObservableMapTest.suite());
		addTest(SetDetailValueObservableMapTest.suite());

		addTestSuite(MapSimpleValueObservableMapTest.class);
		addTestSuite(SetSimpleValueObservableMapTest.class);
		addTestSuite(ListSimpleValueObservableListTest.class);

		addTestSuite(AbstractStringToNumberValidatorTest.class);
		addTestSuite(NumberToByteValidatorTest.class);
		addTestSuite(NumberToDoubleValidatorTest.class);
		addTestSuite(NumberToFloatValidatorTest.class);
		addTestSuite(NumberToIntegerValidatorTest.class);
		addTestSuite(NumberToLongValidatorTest.class);
		addTestSuite(NumberToShortValidatorTest.class);
		addTestSuite(NumberToUnboundedNumberValidatorTest.class);
		addTestSuite(StringToByteValidatorTest.class);
		addTestSuite(StringToCharacterValidatorTest.class);
		addTestSuite(StringToDoubleValidatorTest.class);
		addTestSuite(StringToFloatValidatorTest.class);
		addTestSuite(StringToIntegerValidatorTest.class);
		addTestSuite(StringToLongValidatorTest.class);
		addTestSuite(StringToShortValidatorTest.class);

		addTest(BindingScenariosTestSuite.suite());

		addTestSuite(SWTObservablesTest.class);
		addTestSuite(WidgetPropertiesTest.class);
		addTestSuite(WidgetObservableThreadTest.class);

		addTestSuite(PreferencePageSupportTest.class);

		addTestSuite(ObservableListContentProviderTest.class);
		addTestSuite(ObservableListTreeContentProviderTest.class);
		addTestSuite(ObservableMapLabelProviderTest.class);
		addTestSuite(ObservableSetContentProviderTest.class);
		addTestSuite(ObservableSetTreeContentProviderTest.class);
		addTestSuite(ObservableValueEditingSupportTest.class);
		addTestSuite(ViewersObservablesTest.class);
		addTestSuite(ViewerSupportTest.class);

		addTestSuite(WizardPageSupportTest.class);

		addTestSuite(EditMaskLexerAndTokenTest.class);
		addTestSuite(EditMaskParserTest.class);

		addTest(ButtonObservableValueTest.suite());
		addTestSuite(CComboObservableValueTest.class);
		addTest(CComboObservableValueSelectionTest.suite());
		addTest(CComboObservableValueTextTest.suite());
		addTestSuite(CComboSingleSelectionObservableValueTest.class);
		addTest(CComboSingleSelectionObservableValueTest.suite());
		addTest(CLabelObservableValueTest.suite());
		addTestSuite(ComboObservableValueTest.class);
		addTest(ComboObservableValueSelectionTest.suite());
		addTest(ComboObservableValueTextTest.suite());
		addTestSuite(ComboSingleSelectionObservableValueTest.class);
		addTestSuite(DateTimeCalendarObservableValueTest.class);
		addTestSuite(DateTimeDateObservableValueTest.class);
		addTestSuite(DateTimeSelectionPropertyTest.class);
		addTestSuite(DateTimeTimeObservableValueTest.class);
		addTest(SWTDelayedObservableValueDecoratorTest.suite());

		addTestSuite(ControlObservableValueTest.class);
		addTest(LabelObservableValueTest.suite());
		addTest(GroupObservableValueTest.suite());
		addTestSuite(ListSingleSelectionObservableValueTest.class);
		addTest(ScaleObservableValueMinTest.suite());
		addTest(ScaleObservableValueMaxTest.suite());
		addTest(ScaleObservableValueSelectionTest.suite());

		addTest(ShellObservableValueTest.suite());

		addTestSuite(SpinnerObservableValueTest.class);
		addTest(SpinnerObservableValueMinTest.suite());
		addTest(SpinnerObservableValueMaxTest.suite());
		addTest(SpinnerObservableValueSelectionTest.suite());

		addTestSuite(TableObservableValueTest.class);
		addTest(TableSingleSelectionObservableValueTest.suite());
		addTest(TextEditableObservableValueTest.suite());
		addTest(TextObservableValueDefaultSelectionTest.suite());
		addTest(TextObservableValueFocusOutTest.suite());
		addTest(TextObservableValueModifyTest.suite());
		addTestSuite(TextObservableValueTest.class);
		addTest(StyledTextObservableValueDefaultSelectionTest.suite());
		addTest(StyledTextObservableValueFocusOutTest.suite());
		addTest(StyledTextObservableValueModifyTest.suite());
		addTestSuite(StyledTextObservableValueTest.class);

		addTestSuite(CheckableCheckedElementsObservableSetTest.class);
		addTest(ObservableViewerElementSetTest.suite());
		addTestSuite(ObservableCollectionContentProviderTest.class);
		addTestSuite(ObservableCollectionTreeContentProviderTest.class);
		addTestSuite(SelectionProviderMultiSelectionObservableListTest.class);
		addTestSuite(SelectionProviderSingleSelectionObservableValueTest.class);
		addTestSuite(ViewerElementMapTest.class);
		addTestSuite(ViewerElementSetTest.class);
		addTestSuite(ViewerElementWrapperTest.class);
		addTest(ViewerInputObservableValueTest.suite());
	}
