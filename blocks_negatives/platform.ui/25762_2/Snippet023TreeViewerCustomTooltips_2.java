					case SWT.MouseDown:
						Event e = new Event ();
						e.item = (TreeItem) label.getData ("_TABLEITEM");
						v.getTree().setSelection (new TreeItem [] {(TreeItem) e.item});
						v.getTree().notifyListeners (SWT.Selection, e);
						shell.dispose ();
						v.getTree().setFocus();
						break;
					case SWT.MouseExit:
						shell.dispose ();
						break;
