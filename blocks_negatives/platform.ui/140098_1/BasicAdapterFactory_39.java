	protected BasicSwitch<Adapter> modelSwitch =
		new BasicSwitch<Adapter>() {
			@Override
			public Adapter casePartDescriptor(MPartDescriptor object) {
				return createPartDescriptorAdapter();
			}
			@Override
			public Adapter casePartDescriptorContainer(MPartDescriptorContainer object) {
				return createPartDescriptorContainerAdapter();
			}
			@Override
			public Adapter caseApplicationElement(MApplicationElement object) {
				return createApplicationElementAdapter();
			}
			@Override
			public Adapter caseLocalizable(MLocalizable object) {
				return createLocalizableAdapter();
			}
			@Override
			public Adapter caseUILabel(MUILabel object) {
				return createUILabelAdapter();
			}
			@Override
			public Adapter caseHandlerContainer(MHandlerContainer object) {
				return createHandlerContainerAdapter();
			}
			@Override
			public Adapter caseBindings(MBindings object) {
				return createBindingsAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};
