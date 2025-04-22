				ViewModel viewModel = new ViewModel();
				Shell shell = new View(viewModel).createShell();

				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}

				System.out.println(viewModel.getText());
			}
		});
		display.dispose();// Test
	}

	public static abstract class AbstractModelObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
				this);

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(listener);
		}

		public void addPropertyChangeListener(String propertyName,
				PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName,
					listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(String propertyName,
				PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(propertyName,
					listener);
		}

		protected void firePropertyChange(String propertyName, Object oldValue,
				Object newValue) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue,
					newValue);
		}
	}

	public static class ViewModel extends AbstractModelObject {
		private String text = "beef";

		private List choices = new ArrayList();
		{
			choices.add("pork");
			choices.add("beef");
			choices.add("poultry");
			choices.add("vegatables");
		}

		public List getChoices() {
			return choices;
		}

		public void setChoices(List choices) {
			List old = this.choices;
			this.choices = choices;
			firePropertyChange("choices", old, choices);
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			String oldValue = this.text;
			this.text = text;
			firePropertyChange("text", oldValue, text);
		}
	}

	static class View {
		private ViewModel viewModel;
		static int count;

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

		public Shell createShell() {
			Shell shell = new Shell(Display.getCurrent());
			shell.setLayout(new RowLayout(SWT.VERTICAL));

			Combo combo = new Combo(shell, SWT.BORDER | SWT.READ_ONLY);
			Button reset = new Button(shell, SWT.NULL);
			reset.setText("reset collection");
			reset.addSelectionListener(new SelectionAdapter() {
				@Override
