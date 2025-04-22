				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						MyModel el = new MyModel(++COUNTER);
						v.add(el);
						model.add(el);
					}
