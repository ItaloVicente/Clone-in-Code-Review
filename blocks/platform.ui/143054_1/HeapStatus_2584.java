				}
				break;
			case SWT.MouseEnter:
				HeapStatus.this.updateTooltip = true;
				updateToolTip();
				break;
			case SWT.MouseExit:
				if (event.widget == HeapStatus.this) {
					HeapStatus.this.updateTooltip = false;
