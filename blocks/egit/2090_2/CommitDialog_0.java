	class CommitMessageArea extends SpellcheckableMessageArea {

		public CommitMessageArea(Composite parent, String initialText) {
			super(parent, initialText);
		}

		protected void configureToolbar(ToolBar toolbar) {
			amendToolItem = new ToolItem(toolbar, SWT.CHECK);
			amendToolItem.setImage(UIIcons.ELCL16_AMEND.createImage());
			amendToolItem.setToolTipText("Amend previous commit"); //$NON-NLS-1$

			new ToolItem(toolbar, SWT.SEPARATOR);

			signedOffToolItem = new ToolItem(toolbar, SWT.CHECK);
		    signedOffToolItem.setImage(UIIcons.ELCL16_AUTHOR.createImage());
		    signedOffToolItem.setToolTipText("Add 'Signed-off-by'"); //$NON-NLS-1$

		    changeIdToolItem = new ToolItem(toolbar, SWT.CHECK);
		    changeIdToolItem.setImage(UIIcons.GERRIT.createImage());
		    changeIdToolItem.setToolTipText("Generate 'Change-Id' for Gerrit"); //$NON-NLS-1$
		}

	}

