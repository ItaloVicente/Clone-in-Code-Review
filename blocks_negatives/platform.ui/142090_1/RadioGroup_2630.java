   private final IRadioButton[] buttons;
   private final Object[] values;
   IRadioButton oldSelection = null;
   IRadioButton selectedButton = null;
   IRadioButton potentialNewSelection = null;

   /** (Non-API)
    * Interface IRadioButton.  A duck interface that is used internally by RadioGroup
    * and by RadioGroup's unit tests.
    */
   public static interface IRadioButton {
      void setData(String string, Object object);
      void addSelectionListener(SelectionListener selectionListener);
      void setSelection(boolean b);
      boolean getSelection();
      boolean isFocusControl();
      String getText();
      void setText(String string);
      void notifyListeners(int eventType, Event object);
   }

   /**
    * Constructs an instance of this widget given an array of Button objects to wrap.
    * The Button objects must have been created with the SWT.RADIO style bit set,
    * and they must all be in the same Composite.
    *
    * @param radioButtons Object[] an array of radio buttons to wrap.
    * @param values Object[] an array of objects corresponding to the value of each radio button.
    */
   public RadioGroup(Object[] radioButtons, Object[] values) {
      IRadioButton[] buttons = new IRadioButton[radioButtons.length];
      if (buttons.length < 1) {
         throw new IllegalArgumentException("A RadioGroup must manage at least one Button");
      }
      for (int i = 0; i < buttons.length; i++) {
         if (!DuckType.instanceOf(IRadioButton.class, radioButtons[i])) {
            throw new IllegalArgumentException("A radio button was not passed");
         }
         buttons[i] = (IRadioButton) DuckType.implement(IRadioButton.class, radioButtons[i]);
         buttons[i].setData(Integer.toString(i), Integer.valueOf(i));
         buttons[i].addSelectionListener(selectionListener);
      }
      this.buttons = buttons;
      this.values = values;
   }

   /**
    * Returns the object corresponding to the currently-selected radio button
    * or null if no radio button is selected.
    *
    * @return the object corresponding to the currently-selected radio button
    * or null if no radio button is selected.
    */
   public Object getSelection() {
      int selectionIndex = getSelectionIndex();
      if (selectionIndex < 0)
         return "";
      return values[selectionIndex];
   }

   /**
    * Sets the selected radio button to the radio button whose model object
    * equals() the object specified by newSelection.  If !newSelection.equals()
    * any model object managed by this radio group, deselects all radio buttons.
    *
    * @param newSelection A model object corresponding to one of the model
    * objects associated with one of the radio buttons.
    */
   public void setSelection(Object newSelection) {
      deselectAll();
      for (int i = 0; i < values.length; i++) {
         if (values[i].equals(newSelection)) {
            setSelection(i);
            return;
         }
      }
   }

   private SelectionListener selectionListener = new SelectionListener() {
      @Override
