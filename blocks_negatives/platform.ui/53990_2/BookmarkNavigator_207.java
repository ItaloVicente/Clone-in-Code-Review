			return adapterClass.cast(new IShowInSource() {
                @Override
				public ShowInContext getShowInContext() {
                    return new ShowInContext(null, getViewer().getSelection());
                }
			});
