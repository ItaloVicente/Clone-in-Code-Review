	public void testEquals11() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(), new StructuredSelection());
	}

	public void testEquals12() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection((TreePath) null),
				new StructuredSelection());
	}

	public void testEquals13() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(newTreePath("element")),
				new StructuredSelection("element"));
	}

	public void testEquals14() {
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(new TreeSelection(newTreePath("element 1")),
				new StructuredSelection("element 2"));
	}

	public void testEquals15() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(newTreePath("element 1", "element 2")),
				new StructuredSelection(Arrays.asList("element 2")));
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(newTreePath("element 1", "element 2")),
				new StructuredSelection(new Object[] { "element 2", }));
	}

	public void testEquals16() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(
						newTreePaths(newTreePath("element 1", "element 2"), newTreePath("element 3", "element 4"))),
				new StructuredSelection(Arrays.asList("element 2", "element 4")));
	}

	public void testEquals17() {
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(
				new TreeSelection(
						newTreePaths(newTreePath("element 1", "element 2"), newTreePath("element 3", "element 4"))),
				new StructuredSelection(Arrays.asList("element 4", "element 2")));
	}

	public void testEquals18() {
		doTestEquals18(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals18(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals18(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection((TreePath) null, comparer), new StructuredSelection(new ArrayList<>(), comparer));
	}

	public void testEquals19() {
		doTestEquals19(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals19(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals19(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(newTreePath("element 1", "element 2"), comparer),
				new StructuredSelection(Arrays.asList("element 2"), comparer));
	}

	public void testEquals20() {
		doTestEquals20(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals20(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals20(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(
						newTreePaths(newTreePath("element 1", "element 2"), newTreePath("element 3", "element 4")),
						comparer),
				new StructuredSelection(Arrays.asList("element 2", "element 4"), comparer));
	}

	public void testEquals21() {
		doTestEquals21(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals21(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals21(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(
				new TreeSelection(newTreePath("element 1"), comparer),
				new StructuredSelection(Arrays.asList("element 2"), comparer));
	}

	public void testEquals22() {
		doTestEquals22(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals22(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals22(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(
				new TreeSelection(newTreePath("element 1"), comparer),
				new StructuredSelection(new ArrayList<>(), comparer));
	}

	public void testEquals23() {
		doTestEquals23(StructuredSelectionTest.JAVA_LANG_OBJECT_COMPARER);
		doTestEquals23(StructuredSelectionTest.IDENTITY_COMPARER);
	}

	private void doTestEquals23(IElementComparer comparer) {
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(new TreeSelection((TreePath) null, comparer),
				new StructuredSelection(Arrays.asList("element"), comparer));
	}


	private static TreePath newTreePath(Object... args) {
		return new TreePath(args);
	}

	private static TreePath[] newTreePaths(TreePath... args) {
		return args;
	}
