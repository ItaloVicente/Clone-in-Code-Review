						TreeIterator<EObject> treeIt = EcoreUtil.getAllContents(o, true);
						while (treeIt.hasNext()) {
							EObject eObj = treeIt.next();
							r = (E4XMIResource) eObj.eResource();
							if (contributorURI != null && (eObj instanceof MApplicationElement))
								((MApplicationElement) eObj).setContributorURI(contributorURI);
							applicationResource.setID(eObj, r.getInternalId(eObj));
						}
					}
