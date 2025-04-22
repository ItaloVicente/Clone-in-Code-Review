					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append("/");
						rec.append(name);
						rec.append(" ");
						List<String> l = new ArrayList<>();
						for (String group : proj.getGroups()) {
							rec.append(group);
							rec.append(" ");
						}
						rec.append("\n");
						attributes.append(rec.toString());
					}
