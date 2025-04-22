			if (iconDescriptor != null) {
				menuEntry.setImageDescriptor(iconDescriptor);
			} else if (parent.getParent() == null) {
				menuEntry.setImageDescriptor(menuImageDescriptor);
			} else {
				menuEntry.setImageDescriptor(submenuImageDescriptor);
			}
