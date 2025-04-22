   }


   /**
    * Adds the listener to the collection of listeners who will
    * be notified when the receiver's selection is about to change, by sending
    * it one of the messages defined in the <code>VetoableSelectionListener</code>
    * interface.
    * <p>
    * <code>widgetSelected</code> is called when the selection changes.
    * <code>widgetDefaultSelected</code> is typically called when an item is double-clicked.
    * </p>
    *
    * @param listener the listener which should be notified
    *
    * @exception IllegalArgumentException <ul>
    *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
    * </ul>
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    *
    * @see VetoableSelectionListener
    * @see #removeVetoableSelectionListener
    * @see SelectionEvent
    */
   public void addVetoableSelectionListener(VetoableSelectionListener listener) {
      widgetChangeListeners.add(listener);
   }

   /**
    * Removes the listener from the collection of listeners who will
    * be notified when the receiver's selection is about to change.
    *
    * @param listener the listener which should no longer be notified
    *
    * @exception IllegalArgumentException <ul>
    *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
    * </ul>
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    *
    * @see VetoableSelectionListener
    * @see #addVetoableSelectionListener
    */
   public void removeVetoableSelectionListener(VetoableSelectionListener listener) {
      widgetChangeListeners.remove(listener);
   }


   private List<SelectionListener> widgetSelectedListeners = new ArrayList<>();

   protected void fireWidgetSelectedEvent(SelectionEvent e) {
      for (SelectionListener listener : widgetSelectedListeners) {
         listener.widgetSelected(e);
      }
   }

   protected void fireWidgetDefaultSelectedEvent(SelectionEvent e) {
      fireWidgetSelectedEvent(e);
   }

   /**
    * Adds the listener to the collection of listeners who will
    * be notified when the receiver's selection changes, by sending
    * it one of the messages defined in the <code>SelectionListener</code>
    * interface.
    * <p>
    * <code>widgetSelected</code> is called when the selection changes.
    * <code>widgetDefaultSelected</code> is typically called when an item is double-clicked.
    * </p>
    *
    * @param listener the listener which should be notified
    *
    * @exception IllegalArgumentException <ul>
    *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
    * </ul>
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    *
    * @see SelectionListener
    * @see #removeSelectionListener
    * @see SelectionEvent
    */
   public void addSelectionListener(SelectionListener listener) {
      widgetSelectedListeners.add(listener);
   }

   /**
    * Removes the listener from the collection of listeners who will
    * be notified when the receiver's selection changes.
    *
    * @param listener the listener which should no longer be notified
    *
    * @exception IllegalArgumentException <ul>
    *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
    * </ul>
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    *
    * @see SelectionListener
    * @see #addSelectionListener
    */
   public void removeSelectionListener(SelectionListener listener) {
      widgetSelectedListeners.remove(listener);
   }

   /**
    * Deselects the item at the given zero-relative index in the receiver.
    * If the item at the index was already deselected, it remains
    * deselected. Indices that are out of range are ignored.
    *
    * @param index the index of the item to deselect
    *
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    */
   public void deselect (int index) {
      if (index < 0 || index >= buttons.length)
         return;
      buttons[index].setSelection(false);
   }

   /**
    * Deselects all selected items in the receiver.
    *
    * @exception SWTException <ul>
    *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
    *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
    * </ul>
    */
