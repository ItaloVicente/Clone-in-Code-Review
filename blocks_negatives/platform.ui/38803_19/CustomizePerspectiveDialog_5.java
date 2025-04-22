					if (iconDescriptor == null) {
						if (parent != null && parent.getParent() == null) {
							menuEntry.setImageDescriptor(menuImageDescriptor);
						} else if (menuEntry.getChildren().size() > 0) {
							menuEntry.setImageDescriptor(submenuImageDescriptor);
						}
