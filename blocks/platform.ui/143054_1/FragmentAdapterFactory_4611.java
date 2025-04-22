	protected FragmentSwitch<Adapter> modelSwitch = new FragmentSwitch<Adapter>() {
		@Override
		public Adapter caseModelFragments(MModelFragments object) {
			return createModelFragmentsAdapter();
		}

		@Override
		public Adapter caseModelFragment(MModelFragment object) {
			return createModelFragmentAdapter();
		}

		@Override
		public Adapter caseStringModelFragment(MStringModelFragment object) {
			return createStringModelFragmentAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};
