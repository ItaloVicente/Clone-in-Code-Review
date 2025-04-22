				if (newText.length() == 0) {
					if (errorOnEmptyName)
						return UIText.ValidationUtils_PleaseEnterNameMessage;
					else
						return null;
				}
