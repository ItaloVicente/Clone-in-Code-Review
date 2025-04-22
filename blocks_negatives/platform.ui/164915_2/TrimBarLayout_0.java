	/**
	 * This is a HACK ! Due to compatibility restrictions we have the case where
	 * we <b>must</b> leave 'empty' toolbars in the trim. This code detects this
	 * particular scenario and hides any TB's of this type...
	 *
	 * @param te
	 *            The proposed trim element
	 * @return <code>true</code> iff this element represents an empty managed
	 *         TB.
	 */
	private boolean hideManagedTB(MTrimElement te) {
		if (!(te instanceof MToolBar) || !(te.getRenderer() instanceof ToolBarManagerRenderer))
			return false;

		if (!(te.getWidget() instanceof Composite))
			return false;

		Composite teComp = (Composite) te.getWidget();
		Control[] kids = teComp.getChildren();
		if (kids.length != 1 || !(kids[0] instanceof ToolBar))
			return false;

		boolean barVisible = ((ToolBar) kids[0]).getItemCount() > 0;

			te.setVisible(barVisible);
		}

		return !barVisible;
	}

