	/**
	 * Sets a layout object, which is used for the setLayout method of the control.
	 * This method can be used if only one control should be created with this
	 * factory.<br />
	 * Use {@link #layoutData(Supplier)} if several controls with the same instance
	 * of factory should be created. LayoutData should be unique and they should not
	 * be shared among controls.
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * GridData gd = new GridData(GridData.FILL_BOTH);
	 * ButtonFactory.newButton(SWT.PUSH).layoutData(gd);
	 * </pre>
	 * </p>
	 *
	 * @param layoutData
	 * @return this
	 */
	public F layoutData(Object layoutData) {
		addProperty(c -> c.setLayoutData(layoutData));
		return cast(this);
	}

