	class E4PartWrapper extends ViewPart {
		MPart wrappedPart;

		E4PartWrapper(MPart part) {
			wrappedPart = part;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt
		 * .widgets.Composite)
		 */
		@Override
		public void createPartControl(Composite parent) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
		 */
		@Override
		public void setFocus() {
			if (part.getObject() != null && part.getContext() != null)
				ContextInjectionFactory.invoke(part.getObject(), Focus.class, part.getContext());
		}

	}
