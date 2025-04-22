					MModelFragments fragmentsContainer = getFragmentsContainer(ce);
					if (fragmentsContainer == null)
						continue;
					for (MModelFragment fragment : fragmentsContainer.getFragments()) {
						boolean checkExist = !initial && NOTEXISTS.equals(ce.getAttribute("apply")); //$NON-NLS-1$
						fragmentList.add(new ModelFragmentWrapper(fragmentsContainer, fragment,
								ce.getContributor().getName(), URIHelper.constructPlatformURI(ce.getContributor()),
								checkExist)); // $NON-NLS-1$
					}
