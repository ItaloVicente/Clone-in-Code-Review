					getRealm().asyncExec(new Runnable() {
						@Override
						public void run() {
							Integer currentVal = (Integer) getValue();
							setValue(Integer.valueOf(currentVal.intValue() + 1));
						}
