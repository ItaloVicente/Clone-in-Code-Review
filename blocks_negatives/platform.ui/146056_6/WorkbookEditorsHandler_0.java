					MPart model = ref.getModel();
					StyleRange style = new StyleRange();
					style.start = 0;
					style.length = cell.getText().length();
					style.font = getFont(isHiddenEditor(model), isActiveEditor(model));
					cell.setStyleRanges(new StyleRange[] { style });
