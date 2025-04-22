						List<String> themes = new ArrayList<>();
						for (IConfigurationElement cce : cces) {
							String refid = cce.getAttribute("refid");
							List<String> osList = themesToOSVarients.get(refid);
							if(osList != null) {
								osList.forEach(os -> themes.add(refid + "_" + os));
							}
							themes.add(refid);
