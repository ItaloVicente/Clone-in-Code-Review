        return new IShowInSource() {
            @Override
			public ShowInContext getShowInContext() {
                return new ShowInContext(getViewer().getInput(), getViewer()
                        .getSelection());
            }
        };
