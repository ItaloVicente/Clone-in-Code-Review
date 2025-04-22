				int diff = o1.file.getProject().getName().compareTo(
						o2.file.getProject().getName());
				if (diff != 0)
					return diff;
				return o1.file.getProjectRelativePath().toString().compareTo(
						o2.file.getProjectRelativePath().toString());
