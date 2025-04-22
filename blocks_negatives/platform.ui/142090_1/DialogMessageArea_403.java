            return;
        case IMessageProvider.INFORMATION:
            newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
            break;
        case IMessageProvider.WARNING:
            newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
            break;
        case IMessageProvider.ERROR:
            newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);

            break;
        }
        messageComposite.setVisible(true);
        titleLabel.setVisible(false);
        String shortText = Dialog.shortenText(newMessage,messageText);
        if (newMessage.equals(messageText.getToolTipText())
                && newImage == messageImageLabel.getImage()
                	&& shortText.equals(messageText.getText())) {
