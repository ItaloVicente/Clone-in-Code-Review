			} else if (updateEnablement && OpaqueElementUtil.isOpaqueMenuItem(element)) {
				Object obj = OpaqueElementUtil.getOpaqueItem(element);
				if (obj instanceof IContributionItem) {
					IContributionItem ici = (IContributionItem) obj;
					ici.update();
					((MItem) element).setEnabled(ici.isEnabled());
				}
