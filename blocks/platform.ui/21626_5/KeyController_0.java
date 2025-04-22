
		Set<Binding> bindings = new HashSet<Binding>();
		EBindingService eBindingService = (EBindingService) locator
				.getService(EBindingService.class);
		bindings.addAll(eBindingService.getActiveBindings());
		for (Binding binding : bindingService.getBindings()) {
			bindings.add(binding);
		}

		bindingManager.setBindings(bindings.toArray(new Binding[0]));

