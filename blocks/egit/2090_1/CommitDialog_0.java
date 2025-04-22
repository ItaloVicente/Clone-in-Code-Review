	class CommitMessageArea extends SpellcheckableMessageArea {

		public CommitMessageArea(Composite parent, String initialText) {
			super(parent, initialText);
		}

		protected void configureToolbar(ToolBar toolbar) {
			amendToolItem = new ToolItem(toolbar, SWT.PUSH);
			amendToolItem.setImage(UIIcons.ELCL16_COMMENTS.createImage());
			amendToolItem.setToolTipText("Amend previous commit"); //$NON-NLS-1$

			new ToolItem(toolbar, SWT.SEPARATOR);

			signedOffToolItem = new ToolItem(toolbar, SWT.PUSH);
		    signedOffToolItem.setImage(UIIcons.ELCL16_AUTHOR.createImage());
		    signedOffToolItem.setToolTipText("Add 'Signed-off-by'"); //$NON-NLS-1$

		    changeIdToolItem = new ToolItem(toolbar, SWT.PUSH);
		    changeIdToolItem.setImage(UIIcons.GERRIT.createImage());
		    changeIdToolItem.setToolTipText("Generate 'Change-Id' for Gerrit"); //$NON-NLS-1$
		}

	}

