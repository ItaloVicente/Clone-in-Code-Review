				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	public static void sortByName(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}
