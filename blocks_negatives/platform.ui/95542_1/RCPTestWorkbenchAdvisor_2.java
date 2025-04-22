			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					if (isSTARTED())
						asyncDuringStartup = Boolean.FALSE;
					else
						asyncDuringStartup = Boolean.TRUE;
				}
