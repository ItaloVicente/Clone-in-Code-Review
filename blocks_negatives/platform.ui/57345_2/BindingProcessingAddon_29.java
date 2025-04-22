		additionHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object elementObj = event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (elementObj instanceof MApplication) {
					if (UIEvents.isADD(event)) {
						for (Object newObj : UIEvents.asIterable(event,
								UIEvents.EventTags.NEW_VALUE)) {
							if (newObj instanceof MBindingTable) {
								MBindingTable bt = (MBindingTable) newObj;
								final Context bindingContext = contextManager
										.getContext(bt.getBindingContext()
												.getElementId());
								final BindingTable table = new BindingTable(
										bindingContext);
								bindingTables.addTable(table);
								List<MKeyBinding> bindings = bt.getBindings();
								for (MKeyBinding binding : bindings) {
									Binding keyBinding = createBinding(
											bindingContext,
											binding.getCommand(),
											binding.getParameters(),
											binding.getKeySequence(), binding);
									if (keyBinding != null) {
										table.addBinding(keyBinding);
									}
