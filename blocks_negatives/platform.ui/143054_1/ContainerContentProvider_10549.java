                        if (member.getType() != IResource.FILE) {
                            children.add(member);
                        }
                    }
                    return children.toArray();
                } catch (CoreException e) {
                }
            }
        }
        return new Object[0];
    }
