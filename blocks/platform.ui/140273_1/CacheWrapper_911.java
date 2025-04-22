			CacheWrapper.this.flushCache();
		}
	}

	public CacheWrapper(Composite parent) {
		proxy = new Composite(parent, SWT.NONE);

		proxy.setLayout(new WrapperLayout());
	}

	public void flushCache() {
		cache.flush();
	}

	public Composite getControl() {
		return proxy;
	}

	public void dispose() {
		if (proxy != null) {
			proxy.dispose();
			proxy = null;
		}
	}
