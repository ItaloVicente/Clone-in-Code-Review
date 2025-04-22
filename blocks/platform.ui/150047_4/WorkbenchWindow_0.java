				private void removeSaveOnCloseNotNeededSplitEditorParts(List<MPart> parts) {
					for (Iterator<MPart> it = parts.iterator(); it.hasNext();) {
						MPart part = it.next();
						if (isSaveOnCloseNotNeededSplitEditorPart(part)) {
							it.remove();
						}
					}
				}

				private boolean isSaveOnCloseNotNeededSplitEditorPart(MPart part) {
					boolean notNeeded = false;
					if (part instanceof MCompositePart
							&& SplitHost.SPLIT_HOST_CONTRIBUTOR_URI.equals(part.getContributionURI())) {
						MCompositePart compPart = (MCompositePart) part;
						List<MPart> elements = modelService.findElements(compPart, null, MPart.class);
						if (elements != null && elements.size() > 1) {
							elements.remove(0);
							for (MPart mpart : elements) {
								Object object = mpart.getObject();
								if (object instanceof CompatibilityPart) {
									IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
									if (!SaveableHelper.isSaveable(workbenchPart)) {
										notNeeded = true;
									} else {
										ISaveablePart saveable = SaveableHelper.getSaveable(workbenchPart);
										if (saveable == null || !saveable.isSaveOnCloseNeeded()) {
											notNeeded = true;
										} else {
											notNeeded = false;
											break;
										}
									}
								} else {
									notNeeded = false;
									break;
								}
							}
						}
					}
					return notNeeded;
				}

