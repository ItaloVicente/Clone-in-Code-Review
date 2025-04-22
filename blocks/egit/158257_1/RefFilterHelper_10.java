				IMatcher expanded = expandMacros();
				if (expanded != null) {
					expandedFilterPattern = expanded;
				} else {
					expandedFilterPattern = filterPattern;
				}
