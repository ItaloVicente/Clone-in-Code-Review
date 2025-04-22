        if (((accelerator & SWT.KEY_MASK) == 0) && (accelerator != 0)) {
            naturalKey = null;
        } else {
            accelerator &= SWT.KEY_MASK;

            switch (accelerator) {
            case SWT.ARROW_DOWN:
                naturalKey = SpecialKey.ARROW_DOWN;
                break;
            case SWT.ARROW_LEFT:
                naturalKey = SpecialKey.ARROW_LEFT;
                break;
            case SWT.ARROW_RIGHT:
                naturalKey = SpecialKey.ARROW_RIGHT;
                break;
            case SWT.ARROW_UP:
                naturalKey = SpecialKey.ARROW_UP;
                break;
            case SWT.BREAK:
                naturalKey = SpecialKey.BREAK;
                break;
            case SWT.CAPS_LOCK:
                naturalKey = SpecialKey.CAPS_LOCK;
                break;
            case SWT.END:
                naturalKey = SpecialKey.END;
                break;
            case SWT.F1:
                naturalKey = SpecialKey.F1;
                break;
            case SWT.F10:
                naturalKey = SpecialKey.F10;
                break;
            case SWT.F11:
                naturalKey = SpecialKey.F11;
                break;
            case SWT.F12:
                naturalKey = SpecialKey.F12;
                break;
            case SWT.F2:
                naturalKey = SpecialKey.F2;
                break;
            case SWT.F3:
                naturalKey = SpecialKey.F3;
                break;
            case SWT.F4:
                naturalKey = SpecialKey.F4;
                break;
            case SWT.F5:
                naturalKey = SpecialKey.F5;
                break;
            case SWT.F6:
                naturalKey = SpecialKey.F6;
                break;
            case SWT.F7:
                naturalKey = SpecialKey.F7;
                break;
            case SWT.F8:
                naturalKey = SpecialKey.F8;
                break;
            case SWT.F9:
                naturalKey = SpecialKey.F9;
                break;
            case SWT.HOME:
                naturalKey = SpecialKey.HOME;
                break;
            case SWT.INSERT:
                naturalKey = SpecialKey.INSERT;
                break;
            case SWT.KEYPAD_0:
                naturalKey = SpecialKey.NUMPAD_0;
                break;
            case SWT.KEYPAD_1:
                naturalKey = SpecialKey.NUMPAD_1;
                break;
            case SWT.KEYPAD_2:
                naturalKey = SpecialKey.NUMPAD_2;
                break;
            case SWT.KEYPAD_3:
                naturalKey = SpecialKey.NUMPAD_3;
                break;
            case SWT.KEYPAD_4:
                naturalKey = SpecialKey.NUMPAD_4;
                break;
            case SWT.KEYPAD_5:
                naturalKey = SpecialKey.NUMPAD_5;
                break;
            case SWT.KEYPAD_6:
                naturalKey = SpecialKey.NUMPAD_6;
                break;
            case SWT.KEYPAD_7:
                naturalKey = SpecialKey.NUMPAD_7;
                break;
            case SWT.KEYPAD_8:
                naturalKey = SpecialKey.NUMPAD_8;
                break;
            case SWT.KEYPAD_9:
                naturalKey = SpecialKey.NUMPAD_9;
                break;
            case SWT.KEYPAD_ADD:
                naturalKey = SpecialKey.NUMPAD_ADD;
                break;
            case SWT.KEYPAD_CR:
                naturalKey = SpecialKey.NUMPAD_ENTER;
                break;
            case SWT.KEYPAD_DECIMAL:
                naturalKey = SpecialKey.NUMPAD_DECIMAL;
                break;
            case SWT.KEYPAD_DIVIDE:
                naturalKey = SpecialKey.NUMPAD_DIVIDE;
                break;
            case SWT.KEYPAD_EQUAL:
                naturalKey = SpecialKey.NUMPAD_EQUAL;
                break;
            case SWT.KEYPAD_MULTIPLY:
                naturalKey = SpecialKey.NUMPAD_MULTIPLY;
                break;
            case SWT.KEYPAD_SUBTRACT:
                naturalKey = SpecialKey.NUMPAD_SUBTRACT;
                break;
            case SWT.NUM_LOCK:
                naturalKey = SpecialKey.NUM_LOCK;
                break;
            case SWT.PAGE_DOWN:
                naturalKey = SpecialKey.PAGE_DOWN;
                break;
            case SWT.PAGE_UP:
                naturalKey = SpecialKey.PAGE_UP;
                break;
            case SWT.PAUSE:
                naturalKey = SpecialKey.PAUSE;
                break;
            case SWT.PRINT_SCREEN:
                naturalKey = SpecialKey.PRINT_SCREEN;
                break;
            case SWT.SCROLL_LOCK:
                naturalKey = SpecialKey.SCROLL_LOCK;
                break;
            default:
                naturalKey = CharacterKey
                        .getInstance((char) (accelerator & 0xFFFF));
            }
        }

        return KeyStroke.getInstance(modifierKeys, naturalKey);
    }

    /**
     * <p>
     * Converts the given event into an SWT accelerator value -- considering the
     * modified character with the shift modifier. This is the third accelerator
     * value that should be checked.
     * </p>
     * <p>
     * For example, on a standard US keyboard, "Ctrl+Shift+5" would be viewed as
     * "Ctrl+Shift+%".
     * </p>
     *
     * @param event
     *            The event to be converted; must not be <code>null</code>.
     * @return The combination of the state mask and the unmodified character.
     */
    public static int convertEventToModifiedAccelerator(Event event) {
        int modifiers = event.stateMask & SWT.MODIFIER_MASK;
        char character = topKey(event);
        return modifiers + toUpperCase(character);
    }

    /**
     * <p>
     * Converts the given event into an SWT accelerator value -- considering the
     * unmodified character with all modifier keys. This is the first
     * accelerator value that should be checked. However, all alphabetic
     * characters are considered as their uppercase equivalents.
     * </p>
     * <p>
     * For example, on a standard US keyboard, "Ctrl+Shift+5" would be viewed as
     * "Ctrl+Shift+5".
     * </p>
     *
     * @param event
     *            The event to be converted; must not be <code>null</code>.
     * @return The combination of the state mask and the unmodified character.
     */
    public static int convertEventToUnmodifiedAccelerator(Event event) {
        return convertEventToUnmodifiedAccelerator(event.stateMask,
                event.keyCode);
    }

    /**
     * <p>
     * Converts the given state mask and key code into an SWT accelerator value --
     * considering the unmodified character with all modifier keys. All
     * alphabetic characters are considered as their uppercase equivalents.
     * </p>
     * <p>
     * For example, on a standard US keyboard, "Ctrl+Shift+5" would be viewed as
     * "Ctrl+Shift+5".
     * </p>
     *
     * @param stateMask
     *            The integer mask of modifiers keys depressed when this was
     *            pressed.
     * @param keyCode
     *            The key that was pressed, before being modified.
     * @return The combination of the state mask and the unmodified character.
     */
    private static int convertEventToUnmodifiedAccelerator(int stateMask,
            int keyCode) {
        int modifiers = stateMask & SWT.MODIFIER_MASK;
        int character = keyCode;
        return modifiers + toUpperCase(character);
    }

    /**
     * <p>
     * Converts the given event into an SWT accelerator value -- considering the
     * unmodified character with all modifier keys. This is the first
     * accelerator value that should be checked. However, all alphabetic
     * characters are considered as their uppercase equivalents.
     * </p>
     * <p>
     * For example, on a standard US keyboard, "Ctrl+Shift+5" would be viewed as
     * "Ctrl+%".
     * </p>
     *
     * @param event
     *            The event to be converted; must not be <code>null</code>.
     * @return The combination of the state mask and the unmodified character.
     */
    public static int convertEventToUnmodifiedAccelerator(KeyEvent event) {
        return convertEventToUnmodifiedAccelerator(event.stateMask,
                event.keyCode);
    }

    /**
     * Converts the given event into an SWT accelerator value -- considering
     * the modified character without the shift modifier. This is the second
     * accelerator value that should be checked. Key strokes with alphabetic
     * natural keys are run through <code>convertEventToUnmodifiedAccelerator</code>
     *
     * @param event
     *            The event to be converted; must not be <code>null</code>.
     * @return The combination of the state mask without shift, and the
     *         modified character.
     */
    public static int convertEventToUnshiftedModifiedAccelerator(Event event) {
        if (Character.isLetter((char) event.keyCode)) {
            return convertEventToUnmodifiedAccelerator(event);
        }

        int modifiers = event.stateMask & (SWT.MODIFIER_MASK ^ SWT.SHIFT);
        char character = topKey(event);
        return modifiers + toUpperCase(character);
    }

    /**
     * Given a key stroke, this method provides the equivalent SWT accelerator
     * value. The functional inverse of <code>convertAcceleratorToKeyStroke</code>.
     *
     * @param keyStroke
     *            The key stroke to convert; must not be <code>null</code>.
     * @return The SWT accelerator value
     */
    public static int convertKeyStrokeToAccelerator(
            final KeyStroke keyStroke) {
        int accelerator = 0;
        final Iterator iterator = keyStroke.getModifierKeys().iterator();

        while (iterator.hasNext()) {
            final ModifierKey modifierKey = (ModifierKey) iterator.next();

            if (modifierKey == ModifierKey.ALT) {
