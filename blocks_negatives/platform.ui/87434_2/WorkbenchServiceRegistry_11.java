				Arrays.sort(locators, new Comparator(){
					@Override
					public int compare(Object o1, Object o2) {
						ServiceLocator loc1 = (ServiceLocator) o1;
						ServiceLocator loc2 = (ServiceLocator) o2;
						int l1 = loc1
								.getService(IWorkbenchLocationService.class)
								.getServiceLevel();
						int l2 = loc2
								.getService(IWorkbenchLocationService.class)
								.getServiceLevel();
						return l1 < l2 ? -1 : (l1 > l2 ? 1 : 0);
					}
