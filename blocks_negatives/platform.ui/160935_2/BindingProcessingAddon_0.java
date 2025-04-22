	private void registerModelListeners() {
		additionHandler = event -> {
			Object elementObj = event
					.getProperty(UIEvents.EventTags.ELEMENT);
			if (elementObj instanceof MApplication) {
				if (UIEvents.isADD(event)) {
					for (Object newObj1 : UIEvents.asIterable(event,
							UIEvents.EventTags.NEW_VALUE)) {
						if (newObj1 instanceof MBindingTable) {
							MBindingTable bt = (MBindingTable) newObj1;
							final Context bindingContext = contextManager
									.getContext(bt.getBindingContext()
											.getElementId());
							final BindingTable table = new BindingTable(
									bindingContext);
							bindingTables.addTable(table);
							List<MKeyBinding> bindings = bt.getBindings();
							for (MKeyBinding binding1 : bindings) {
								Binding keyBinding = createBinding(
										bindingContext,
										binding1.getCommand(),
										binding1.getParameters(),
										binding1.getKeySequence(), binding1);
								if (keyBinding != null) {
									table.addBinding(keyBinding);
								}
							}
						}
					}
				}
			} else if (elementObj instanceof MBindingTable) {
				if (UIEvents.isADD(event)) {
					for (Object newObj2 : UIEvents.asIterable(event,
							UIEvents.EventTags.NEW_VALUE)) {
						if (newObj2 instanceof MKeyBinding) {
							MKeyBinding binding2 = (MKeyBinding) newObj2;
							updateBinding(binding2, true, elementObj);
						}
					}
				}
				else if (UIEvents.isREMOVE(event)) {
					for (Object oldObj1 : UIEvents.asIterable(event,
							UIEvents.EventTags.OLD_VALUE)) {
						if (oldObj1 instanceof MKeyBinding) {
							MKeyBinding binding3 = (MKeyBinding) oldObj1;
							updateBinding(binding3, false, elementObj);
