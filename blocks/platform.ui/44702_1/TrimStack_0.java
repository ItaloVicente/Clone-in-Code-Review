					List<MPart> relevantParts = new ArrayList<MPart>();
					for (MPart part : parts) {
						if (part.isToBeRendered() && part.isVisible()
								&& part.getCurSharedRef() == null
								|| part.getCurSharedRef() != null
								&& part.getCurSharedRef().isToBeRendered()
								&& part.getCurSharedRef().isVisible())
							relevantParts.add(part);
					}
					if (relevantParts.size() > 0)
						partToActivate = relevantParts.get(0);
