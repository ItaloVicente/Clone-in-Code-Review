				public void updateAreaTrim(int areaId, List<? extends IWindowTrim> trim, boolean removeExtra) {
					if (removeExtra) {
						List<IWindowTrim> existingTrim = getAreaTrim(areaId);
						for (IWindowTrim t : existingTrim) {
							if (!existingTrim.contains(t)) {
								removeTrim(t);
							}
						}
					}
					addTrim(areaId, trim.get(0));
					for (int i = 1; i < trim.size(); i++) {
						addTrim(areaId, trim.get(i), trim.get(i - 1));
					}
