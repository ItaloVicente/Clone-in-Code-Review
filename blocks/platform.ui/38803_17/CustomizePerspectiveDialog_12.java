
				if (iconDescriptor == null) {
					if (parent.getParent() == null) {
						menuEntry.setImageDescriptor(menuImageDescriptor);
					} else if (menuEntry.getChildrenCount() > 0) {
						menuEntry.setImageDescriptor(submenuImageDescriptor);
