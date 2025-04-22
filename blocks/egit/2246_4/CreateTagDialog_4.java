				else if (element instanceof Ref) {
					Ref t = (Ref) element;
					name = t.getName().substring(10);
				} else if (element instanceof RevTag) {
					RevTag t = (RevTag) element;
					name = t.getTagName();
				} else
