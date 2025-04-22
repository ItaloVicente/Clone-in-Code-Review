				if (desc.fFontData.length != fFontData.length)
					return false;
				for (int i = 0; i < fFontData.length; i++)
					if (!fFontData[i].equals(desc.fFontData[i]))
						return false;
				return true;
