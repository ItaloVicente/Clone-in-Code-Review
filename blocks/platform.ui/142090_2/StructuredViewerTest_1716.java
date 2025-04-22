			return fgImage;
		}

		public void setSuffix(String suffix) {
			fgSuffix = suffix;
			fireLabelProviderChanged(new LabelProviderChangedEvent(this));
		}
	}

	public StructuredViewerTest(String name) {
		super(name);
	}

	protected void bulkChange(TestModelChange eventToFire) {
		TestElement first = fRootElement.getFirstChild();
		TestElement newElement = first.getContainer().basicAddChild();
		fRootElement.basicDeleteChild(first);
		fModel.fireModelChanged(eventToFire);
