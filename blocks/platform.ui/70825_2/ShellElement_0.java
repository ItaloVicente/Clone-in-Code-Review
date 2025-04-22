				StringBuilder sb = new StringBuilder();
				do {
					String id = WidgetElement.getID(parent);
					if (id != null && id.length() > 0) {
						sb.append(id).append(' ');
					}
					parent = parent.getParent();
				} while (parent != null);
				return sb.toString().trim();
			};
