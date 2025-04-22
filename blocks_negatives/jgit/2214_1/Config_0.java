					if (e.name != null
							&& StringUtils.equalsIgnoreCase(e.section, section)) {
						if (subsection == null && e.subsection == null)
							result.add(StringUtils.toLowerCase(e.name));
						else if (e.subsection != null
								&& e.subsection.equals(subsection))
							result.add(StringUtils.toLowerCase(e.name));

