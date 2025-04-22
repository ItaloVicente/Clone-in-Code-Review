			GridData gd = new GridData();
			gd.horizontalIndent = HINDENT;
			gd.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
			label.setLayoutData(gd);
			if (imageLabel == null) {
				imageLabel = label;
			}

			StyledText styledText = new StyledText(infoArea, textStyle);
			this.texts.add(styledText);
			sampleStyledText = styledText;
			styledText.setCursor(null);
			JFaceColors.setColors(styledText, foreground, background);
