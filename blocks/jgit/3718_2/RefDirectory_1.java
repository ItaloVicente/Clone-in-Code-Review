				for (String f : entries) {
					File e = new File(dir
					if (e.isDirectory())
						directories.put(f
				}
				Arrays.sort(entries
					public int compare(String o1
						File f1 = directories.get(o1);
						File f2 = directories.get(o2);
						if (f1 == null && f2 == null)
							return o1.compareTo(o2);
						if (f1 != null && f2 != null)
							return o1.compareTo(o2);
						String s1 = f1 == null ? o1 : o1 + "/";
						String s2 = f2 == null ? o2 : o2 + "/";
						return s1.compareTo(s2);
					}
				});
