	public void setBuilderFactory(RepositoryBuilderFactory factory) {
		this.factory = factory;
	}

	private BaseRepositoryBuilder<?
		return factory != null ? factory.get() : new RepositoryBuilder();
	}

