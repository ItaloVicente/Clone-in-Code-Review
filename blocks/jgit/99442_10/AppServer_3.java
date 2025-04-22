		return cm;
	}

	private void auth(ServletContextHandler ctx
			String... methods) {
		AbstractLoginService users = new TestMappedLoginService(authRole);
		List<ConstraintMapping> mappings = new ArrayList<>();
		if (methods == null || methods.length == 0) {
			mappings.add(createConstraintMapping());
		} else {
			for (String method : methods) {
				ConstraintMapping cm = createConstraintMapping();
				cm.setMethod(method.toUpperCase(Locale.ROOT));
				mappings.add(cm);
			}
		}
