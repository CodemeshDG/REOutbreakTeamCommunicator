package com.dommyg.reoutbreakteamcommunicator;

class SubStatus {

    String create(boolean[] data, String[] items, String location, StatusType statusType) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (statusType) {
            case PANIC:
                if (data[0]) {
                    stringBuilder.append(" downed");
                }
                if (data[1]) {
                    stringBuilder.append(" in danger status");
                }
                if (data[2]) {
                    stringBuilderCheckComma(stringBuilder);
                    stringBuilder.append(" having a high viral load");
                }
                if (data[3]) {
                    stringBuilderCheckComma(stringBuilder);
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("and");
                    }
                    stringBuilder.append(" trapped");
                }

                stringBuilderLocation(stringBuilder, location);

                if (stringBuilder.toString().length() > 0) {
                    stringBuilder.insert(0, "I'm");
                    stringBuilder.append("!");
                    return stringBuilder.toString().toUpperCase();
                }
                break;

            case NEED:
                boolean requestItem = false;

                for (int i = 0; data.length > i; i++) {
                    if (data[i]) {
                        requestItem = true;
                        if (items[i].length() > 0) {
                            stringBuilderCheckComma(stringBuilder);
                            stringBuilder.append(items[i]);
                        } else {
                            stringBuilderCheckComma(stringBuilder);
                            switch (i) {
                                case 0:
                                    stringBuilder.append("a healing item");
                                    break;

                                case 1:
                                    stringBuilder.append("a weapon");
                                    break;

                                case 2:
                                    stringBuilder.append("ammo");
                                    break;

                                case 3:
                                    stringBuilder.append("a key item");
                                    break;
                            }
                        }
                    }
                }

                stringBuilderLocation(stringBuilder, location);

                if (stringBuilder.toString().length() > 0) {
                    if (requestItem) {
                        stringBuilder.insert(0, "I need ");
                    } else {
                        stringBuilder.insert(0, "I'm");
                    }
                    stringBuilder.append("!");
                    return stringBuilder.toString().toUpperCase();
                }
                break;

            case DEAD:
                boolean hadItem = false;
                int undeclaredItems = 0;

                for (int i = 0; data.length > i; i++) {
                    if (data[i]) {
                        hadItem = true;
                        if (items[i].length() > 0) {
                            stringBuilderCheckComma(stringBuilder);
                            stringBuilder.append(items[i]);
                        } else {
                            undeclaredItems++;
                        }
                    }
                }

                if (stringBuilder.toString().length() > 0 && undeclaredItems > 0) {
                    stringBuilder.append(", and ").append(undeclaredItems).append(" other items");
                } else {
                    stringBuilder.append(undeclaredItems).append(" items");
                }

                stringBuilderLocation(stringBuilder, location);

                if (stringBuilder.toString().length() > 0) {
                    if (hadItem) {
                        stringBuilder.insert(0, "I was carrying ");
                    } else {
                        stringBuilder.insert(0, "I'm");
                    }
                    stringBuilder.append("!");
                    return stringBuilder.toString().toUpperCase();
                }
                break;
        }
        return "";
    }

    private void stringBuilderCheckComma(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(", ");
        }
    }

    private void stringBuilderLocation(StringBuilder stringBuilder, String location) {
        if (location.length() > 0) {
            stringBuilder.append(" @ ").append(location);
        }
    }
}
