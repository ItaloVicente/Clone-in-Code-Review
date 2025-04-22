					setPresentsDefaultValue(false);
					FontData[] newData = new FontData[1];
					newData[0] = font;
					updateFont(newData);
					fireValueChanged(VALUE, oldFont[0], font);
				}
