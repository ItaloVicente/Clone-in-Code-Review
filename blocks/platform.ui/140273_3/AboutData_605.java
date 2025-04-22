				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	public static void sortById(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}
