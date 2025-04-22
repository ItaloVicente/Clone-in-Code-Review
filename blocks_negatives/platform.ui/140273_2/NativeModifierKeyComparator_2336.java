        ModifierKey modifierKeyLeft = (ModifierKey) left;
        ModifierKey modifierKeyRight = (ModifierKey) right;
        int modifierKeyLeftRank = rank(modifierKeyLeft);
        int modifierKeyRightRank = rank(modifierKeyRight);

        if (modifierKeyLeftRank != modifierKeyRightRank) {
            return modifierKeyLeftRank - modifierKeyRightRank;
        }
