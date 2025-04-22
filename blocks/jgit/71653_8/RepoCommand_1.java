					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append("/");
						rec.append(name);
						List<String> l = new ArrayList<>();
						for (String group : proj.getGroups()) {
							rec.append(" ");
							rec.append(group);
						}
						rec.append("\n");
						attributes.append(rec.toString());
					}
