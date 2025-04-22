		if (bean instanceof TextBuiltin) {
			cmd = (TextBuiltin) bean;
		}
		if (repo == null && cmd != null) {
			repo = cmd.getRepository();
		}
