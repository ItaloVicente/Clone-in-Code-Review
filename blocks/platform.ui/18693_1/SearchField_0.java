				if (wasVisible && !nowVisible) {
					removeAccessibleListener();
				}
				if (nowVisible) {
					notifyAccessibleTextChanged();
				}
