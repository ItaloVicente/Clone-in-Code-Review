		if (ici instanceof CommandContributionItem) {
			CommandContributionItem cmd = (CommandContributionItem) ici;
			ImageDescriptor icon = (ImageDescriptor) iconField.get(cmd);
			assertNull(icon);
		} else if (ici instanceof HandledContributionItem) {
			final MHandledItem model = ((HandledContributionItem)ici).getModel();
			String iconString = model.getIconURI();
			assertTrue(iconString, iconString==null || iconString.length()==0);
		}
