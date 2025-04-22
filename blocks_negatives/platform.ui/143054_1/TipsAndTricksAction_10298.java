        IProduct product = Platform.getProduct();
        FeatureSelectionDialog d = new FeatureSelectionDialog(shell, features,
                product == null ? null : product.getId(), IDEWorkbenchMessages.TipsAndTricksPageSelectionDialog_title,
                IDEWorkbenchMessages.TipsAndTricksPageSelectionDialog_message,
                IIDEHelpContextIds.TIPS_AND_TRICKS_PAGE_SELECTION_DIALOG);
        d.create();
        d.getOkButton().setEnabled(false);
