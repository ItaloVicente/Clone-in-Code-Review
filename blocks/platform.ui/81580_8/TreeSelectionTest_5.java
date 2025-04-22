		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(treeSelection1, treeSelection2);
	}

	public void testEquals1() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(), new TreeSelection());
	}

	public void testEquals2() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection((TreePath) null),
				new TreeSelection((TreePath) null));
	}

	public void testEquals3() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection((TreePath[]) null),
				new TreeSelection((TreePath[]) null));
	}

	public void testEquals4() {
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(new TreePath[0]),
				new TreeSelection(new TreePath[0]));
	}

	public void testEquals5() {
		Object one = new Object();
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(newTreePath(one)),
				new TreeSelection(newTreePath(one)));
	}

	public void testEquals6() {
		Object one = new Object();
		Object two = new Object();
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(new TreeSelection(newTreePath(one)),
				new TreeSelection(newTreePath(two)));
	}

	public void testEquals7() {
		Object one = new Object();
		Object two = new Object();
		Object three = new Object();
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(new TreeSelection(newTreePath(one, two, three)),
				new TreeSelection(newTreePath(one, two, three)));
	}

	public void testEquals8() {
		Object one = new Object();
		Object two = new Object();
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(new TreeSelection(newTreePath(one, two)),
				new TreeSelection(newTreePath(two, one)));
	}

	public void testEquals9() {
		Object one = new Object();
		Object two = new Object();
		Object three = new Object();
		Object four = new Object();
		EqualsHashCodeContractTestHelper.testExpectedEqualsObjects(
				new TreeSelection(newTreePaths(newTreePath(one, two), newTreePath(three, four))),
				new TreeSelection(newTreePaths(newTreePath(one, two), newTreePath(three, four))));
	}

	public void testEquals10() {
		Object one = new Object();
		Object two = new Object();
		Object three = new Object();
		Object four = new Object();
		EqualsHashCodeContractTestHelper.testExpectedNotEqualsObjects(
				new TreeSelection(newTreePaths(newTreePath(one, two), newTreePath(three, four))),
				new TreeSelection(newTreePaths(newTreePath(three, four), newTreePath(one, two))));
