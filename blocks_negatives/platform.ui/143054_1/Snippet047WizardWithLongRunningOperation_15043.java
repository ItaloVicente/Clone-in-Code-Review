							parent.getDisplay().asyncExec(new Runnable() {

								@Override
								public void run() {
									MyModel tmp = new MyModel(j);
									v.add(tmp);
									ms.add(tmp);
									bar.setSelection(j + 1);
								}
