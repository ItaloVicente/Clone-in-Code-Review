		Set<Binding> bindings = new HashSet<Binding>();
		bindings.addAll(bindingService.getActiveBindings());
		for (Binding binding : manager.getBindings()) {
			bindings.add(binding);
		}
		return bindings.toArray(new Binding[0]);
