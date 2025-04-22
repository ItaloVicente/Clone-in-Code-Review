				switch (side) {
				case SWT.TOP:
					sb.append(SideValue.TOP_VALUE).append(' ').append(topCounter++);
					break;
				case SWT.BOTTOM:
					sb.append(SideValue.BOTTOM_VALUE).append(' ').append(bottomCounter++);
					break;
				case SWT.RIGHT:
					sb.append(SideValue.RIGHT_VALUE).append(' ').append(rightCounter++);
					break;
				default:
					sb.append(SideValue.LEFT_VALUE).append(' ').append(leftCounter++);
					break;
				}
