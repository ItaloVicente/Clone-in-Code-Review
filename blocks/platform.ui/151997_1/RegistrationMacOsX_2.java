				for (String location : locations) {
					if (getEclipseLauncher().startsWith(location)) {
						schemeInfo.setHandled(true);
					}
					schemeInfo.setHandlerLocation(location);

					returnList.add(schemeInfo);
				}
