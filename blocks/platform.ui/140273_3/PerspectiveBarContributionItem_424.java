			} else {
				toolItem.setText(""); //$NON-NLS-1$
			}
		}
	}

	public void update(IPerspectiveDescriptor newDesc) {
		perspective = newDesc;
		if (toolItem != null && !toolItem.isDisposed()) {
			ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
			if (imageDescriptor != null) {
				toolItem.setImage(imageDescriptor.createImage());
			} else {
				toolItem.setImage(
						WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE).createImage());
			}
			toolItem.setToolTipText(
					NLS.bind(WorkbenchMessages.PerspectiveBarContributionItem_toolTip, perspective.getLabel()));
		}
		update();
	}

	IWorkbenchPage getPage() {
		return workbenchPage;
	}

	IPerspectiveDescriptor getPerspective() {
		return perspective;
	}

	ToolItem getToolItem() {
		return toolItem;
	}

	public boolean handles(IPerspectiveDescriptor perspective, IWorkbenchPage workbenchPage) {
		return this.perspective == perspective && this.workbenchPage == workbenchPage;
	}

	public void setPerspective(IPerspectiveDescriptor newPerspective) {
		this.perspective = newPerspective;
	}

	void setSelection(boolean b) {
		if (toolItem != null && !toolItem.isDisposed()) {
