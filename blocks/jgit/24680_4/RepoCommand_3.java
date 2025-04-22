			this.groups = new HashSet<String>();
			if (groups != null && groups.length() > 0)
				this.groups.addAll(Arrays.asList(groups.split("
			} else {
				for (String group : groups.split("
						minusGroups.add(group.substring(1));
					else
						plusGroups.add(group);
				}
			}
