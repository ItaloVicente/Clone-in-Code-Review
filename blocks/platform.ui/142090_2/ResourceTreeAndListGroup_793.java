		}
		return result;
	}

	public int getCheckedElementCount() {
		return checkedStateStore.size();
	}

	private String getFullLabel(Object treeElement, String parentLabel) {
		String label = parentLabel;
		if (parentLabel == null){
			label = ""; //$NON-NLS-1$
		}
		IPath parentName = new Path(label);

		String elementText = treeLabelProvider.getText(treeElement);
		if(elementText == null) {
