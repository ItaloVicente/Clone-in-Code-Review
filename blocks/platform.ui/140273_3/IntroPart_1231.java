		this.titleImage = titleImage;
		firePropertyChange(IIntroPart.PROP_TITLE);
	}

	protected void setTitle(String titleLabel) {
		Assert.isNotNull(titleLabel);
		if (Util.equals(this.titleLabel, titleLabel))
			return;
		this.titleLabel = titleLabel;
		firePropertyChange(IIntroPart.PROP_TITLE);
	}
