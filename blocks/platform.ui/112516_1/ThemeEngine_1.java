						List<String> themes = new ArrayList<>();
						for (IConfigurationElement cce : cces) {
							String refid = cce.getAttribute("refid");
							List<String> osList = oSsWithThemeVarient.get(refid);
							if(osList != null) {
								for(String oString : osList) {
									themes.add(refid + "_" + oString);
								}
							}
							themes.add(refid);
