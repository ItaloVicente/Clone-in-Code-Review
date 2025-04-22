				elementMem.putString(TAG_ID, String.valueOf(marker.getId()));
			}
		}

		ScrollBar bar = scrollable.getVerticalBar();
		int position = bar != null ? bar.getSelection() : 0;
		memento.putString(TAG_VERTICAL_POSITION, String.valueOf(position));
		bar = scrollable.getHorizontalBar();
		position = bar != null ? bar.getSelection() : 0;
		memento.putString(TAG_HORIZONTAL_POSITION, String.valueOf(position));

	}

	@Override
