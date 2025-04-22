				if (element instanceof RefSpec) {
					RefSpec refSpec = (RefSpec) element;
					if (isDeleteRefSpec(refSpec)) {
						return UIText.RefSpecPanel_forceDeleteDescription;
					} else if (refSpec.isForceUpdate()) {
						return UIText.RefSpecPanel_forceTrueDescription + '\n'
								+ UIText.RefSpecPanel_clickToChange;
					}
					return UIText.RefSpecPanel_forceFalseDescription + '\n'
