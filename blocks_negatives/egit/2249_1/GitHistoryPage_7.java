		}
		super.dispose();
	}

	public void refresh() {
		this.input = null;
		inputSet();
	}

	@Override
	public void setFocus() {
		graph.getControl().setFocus();
