					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append(name);
						List<String> l = new ArrayList<>();
						for (String group : proj.getGroups()) {
							rec.append(group);
						}
						attributes.append(rec.toString());
					}
