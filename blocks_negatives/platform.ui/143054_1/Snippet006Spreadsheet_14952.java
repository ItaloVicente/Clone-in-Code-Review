				if (FUNKY_COUNTER) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							cellFormulas[0][1].setValue("" + counter++);
							display.timerExec(COUNTER_UPDATE_DELAY, this);
						}
					});
