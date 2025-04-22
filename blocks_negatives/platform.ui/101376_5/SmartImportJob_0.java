				Comparator<File> rootToLeafComparator = new Comparator<File>() {
					@Override
					public int compare(File arg0, File arg1) {
						int lengthDiff = arg0.getAbsolutePath().length() - arg1.getAbsolutePath().length();
						if (lengthDiff != 0) {
							return lengthDiff;
						}
						return arg0.compareTo(arg1);
					}
				};
