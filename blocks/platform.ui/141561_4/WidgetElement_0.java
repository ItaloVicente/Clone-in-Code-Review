		case "swt-lines-visible":
			if (widget instanceof Table) {
				return () -> String.valueOf(((Table) widget).getLinesVisible());
			}
			if (widget instanceof Tree) {
				return () -> String.valueOf(((Tree) widget).getLinesVisible());
			}
