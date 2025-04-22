						MModelFragments fragmentsContainer = getFragmentsContainer(ce);
						if (fragmentsContainer == null) {
							continue;
						}
						String contributorURI = URIHelper.constructPlatformURI(ce.getContributor());
						BasicDiagnostic diagnostic = new BasicDiagnostic();
						boolean evalImports = false;
						for (MModelFragment fragment : fragmentsContainer.getFragments()) {
							Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
							int severity = validationResult.getSeverity();
							if (severity == Diagnostic.ERROR) {
								logger.error(
										"Fragment from \"" + "uri.toString()" + "\" of \"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
												+ ce.getContributor().getName()
												+ "\" could not be validated and was not merged \"{0}\"", //$NON-NLS-1$
										fragment.toString());

								continue;
							}
							boolean checkExist = !initial && NOTEXISTS.equals(ce.getAttribute("apply")); //$NON-NLS-1$
							List<MApplicationElement> merged = processFragment(fragment, contributorURI, checkExist);
							if (!merged.isEmpty()) {
								addedElements.addAll(merged);
								evalImports = true;
							} else {
								logger.debug("Nothing to merge for fragment \"{0}\" of \"{1}\"", ce.getAttribute("uri"), //$NON-NLS-1$ //$NON-NLS-2$
										ce.getContributor().getName());
							}
						}
						if (evalImports) {
							imports.addAll(fragmentsContainer.getImports());
						}
						for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
							logger.error(
									"Fragment from \"" + "uri.toString()" + "\" of \"" + ce.getContributor().getName() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
											+ "\" could not be validated and was not merged \"{0}\"", //$NON-NLS-1$
									childDiagnostic.getSource());
						}
