		Repository repository = node.getRepository();
		if (repository == null) {
			return false;
		}

		if (property.equals("isBare")) { //$NON-NLS-1$
			return repository.isBare();
		}
