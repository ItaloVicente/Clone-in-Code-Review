        updated = false;
        getDecoratorManager().clearCaches();
        definition.setEnabled(true);
        getDecoratorManager().updateForEnablementChange();
        definition.setEnabled(false);
        getDecoratorManager().updateForEnablementChange();
        updated = false;
