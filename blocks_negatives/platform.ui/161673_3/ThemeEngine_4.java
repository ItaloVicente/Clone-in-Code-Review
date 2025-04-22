						}
					} else {
						List<String> themes = new ArrayList<>();
						for (IConfigurationElement cce : cces) {
							String refid = cce.getAttribute("refid");
							List<String> varientOSList = themesToVarients.get(refid);
							if (varientOSList != null) {
								themes.addAll(varientOSList);
