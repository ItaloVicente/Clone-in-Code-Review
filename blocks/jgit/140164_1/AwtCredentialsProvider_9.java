            switch (items.length) {
                case 0:
                    return true;
                case 1:
                    final CredentialItem item = items[0];
                    
                    if (item instanceof CredentialItem.InformationalMessage) {
                        JOptionPane.showMessageDialog(null
                                UIText.get().warning
                        return true;
                        
                    } else if (item instanceof CredentialItem.YesNoType) {
                        CredentialItem.YesNoType v = (CredentialItem.YesNoType) item;
                        int r = JOptionPane.showConfirmDialog(null
                                UIText.get().warning
                        switch (r) {
                            case JOptionPane.YES_OPTION:
                                v.setValue(true);
                                return true;
                                
                            case JOptionPane.NO_OPTION:
                                v.setValue(false);
                                return true;
                                
                            case JOptionPane.CANCEL_OPTION:
                            case JOptionPane.CLOSED_OPTION:
                            default:
                                return false;
                        }
                        
                    } else {
                        return interactive(uri
                    }
                default:
                    return interactive(uri
            }
