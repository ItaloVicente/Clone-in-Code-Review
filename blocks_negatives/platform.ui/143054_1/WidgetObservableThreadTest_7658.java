			threadRealm.exec(new Runnable() {
				@Override
				public void run() {
					ctx.dispose();
					ctx = null;
				}
