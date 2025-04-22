			return result * directions[column];
		}
		case FOLDER: {
			String folder1 = BookmarkLabelProvider.getContainerName(marker1);
			String folder2 = BookmarkLabelProvider.getContainerName(marker2);
			int result = getComparator().compare(folder1, folder2);
			if (result == 0) {
