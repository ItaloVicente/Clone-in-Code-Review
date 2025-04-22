							MPart ap = (MPart) w.getContext().get(
									IServiceConstants.ACTIVE_PART);
							if (ap != null) {
								System.out.println("ap: " + ap.getLabel()); //$NON-NLS-1$
								EPartService ps = w.getContext().get(
										EPartService.class);
								ps.activate(ap, true);
							} else {
								w.getContext().activate();
							}
