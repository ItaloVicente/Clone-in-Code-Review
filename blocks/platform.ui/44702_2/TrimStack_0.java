					List<MPart> relevantParts = new ArrayList<MPart>();
					for (MPart part : parts) {
						if (partService.isPartOrPlaceholderInPerspective(part.getElementId(),
								modelService.getActivePerspective(window)))
							relevantParts.add(part);
					}
					if (relevantParts.size() > 0)
						partToActivate = relevantParts.get(0);
