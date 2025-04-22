			return adapterType.cast(new IShowInSource() {
                @Override
				public ShowInContext getShowInContext() {
                    return new ShowInContext(null, getSelection());
                }
			});
