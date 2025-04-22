    private final IRegistryChangeListener registryListener = new IRegistryChangeListener() {

        @Override
		public void registryChanged(IRegistryChangeEvent event) {
            if (getParent() != null) {
                getParent().markDirty();
            }
        }

    };
