					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append(name);
						for (String group : proj.getGroups()) {
							rec.append(group);
						}
						attributes.append(rec.toString());
					}
