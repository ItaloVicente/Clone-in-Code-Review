			keyScrollingFilter = event -> {
				if (!keyScrollingEnabled || sc.isDisposed()) {
					return;
				}
				switch (event.keyCode) {
				case SWT.ARROW_DOWN:
					sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
							+ INCREMENT);
					break;
				case SWT.ARROW_UP:
					sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
							- INCREMENT);
					break;
				case SWT.ARROW_LEFT:
					sc.setOrigin(sc.getOrigin().x - INCREMENT, sc
							.getOrigin().y);
					break;
				case SWT.ARROW_RIGHT:
					sc.setOrigin(sc.getOrigin().x + INCREMENT, sc
							.getOrigin().y);
					break;
				case SWT.PAGE_DOWN:
					sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
							+ PAGE_MULTIPLIER * INCREMENT);
					break;
				case SWT.PAGE_UP:
					sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
							- PAGE_MULTIPLIER * INCREMENT);
					break;
				case SWT.HOME:
					sc.setOrigin(0, 0);
					break;
				case SWT.END:
					sc.setOrigin(0, sc.getSize().y);
					break;
				default:
					keyScrollingEnabled = false;
