
	@Override
	public void testRemoveAtPosition_selectedElement0() {
		this.preRemoveTableItemHookExecutable = new Runnable() {
			@Override
			public void run() {
				fRootElement.fChildren.remove(0);
			}
		};
		super.testRemoveAtPosition_selectedElement0();
	}

	@Override
	public void testRemoveAtPosition_selectedElement1() {
		this.preRemoveTableItemHookExecutable = new Runnable() {
			@Override
			public void run() {
				fRootElement.fChildren.remove(1);
			}
		};
		super.testRemoveAtPosition_selectedElement1();
	}

	@Override
	public void testRemoveAtPosition_selectedElement2() {
		this.preRemoveTableItemHookExecutable = new Runnable() {
			@Override
			public void run() {
				fRootElement.fChildren.remove(2);
			}
		};
		super.testRemoveAtPosition_selectedElement2();
	}

	@Override
	public void testRemoveAtPosition_selectedElement3() {
		this.preRemoveTableItemHookExecutable = new Runnable() {
			@Override
			public void run() {
				fRootElement.fChildren.remove(4);
			}
		};
		super.testRemoveAtPosition_selectedElement3();
	}

	@Override
	public void testRemoveAtPosition_notSelectedElement() {
		this.preRemoveTableItemHookExecutable = new Runnable() {
			@Override
			public void run() {
				fRootElement.fChildren.remove(3);
			}
		};
		super.testRemoveAtPosition_notSelectedElement();
	}
