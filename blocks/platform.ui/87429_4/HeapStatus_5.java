		        }
		        break;
		    case SWT.MouseDown:
		        if (event.button == 1) {
		            if (event.widget == HeapStatus.this) {
						setMark();
					} else if (event.widget == button) {
						if (!isInGC)
							arm(true);
					}
		        }
		        break;
		    case SWT.MouseEnter:
		    	HeapStatus.this.updateTooltip = true;
		    	updateToolTip();
		    	break;
		    case SWT.MouseExit:
		        if (event.widget == HeapStatus.this) {
		        	HeapStatus.this.updateTooltip = false;
				} else if (event.widget == button) {
					arm(false);
				}
		        break;
		    }
		};
