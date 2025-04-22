					if (e.name == null)
						continue;
					if (!StringUtils.equalsIgnoreCase(section
						continue;
					if ((subsection == null && e.subsection == null)
							|| (subsection != null && subsection
									.equals(e.subsection))) {
						String lc = StringUtils.toLowerCase(e.name);
						if (!m.containsKey(lc))
							m.put(lc
