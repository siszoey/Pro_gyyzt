package com.lib.bandaid.utils;

import java.util.Map;

public final class ObjectUtil {

    public static boolean baseTypeIsEqual(Object o1, Object o2, boolean nullEqual) {
        if (nullEqual) {
            if (o1 == null && o2 == null) {
                return true;
            } else if (o1 != null && o2 == null || o1 == null && o2 != null) {
                return false;
            } else {
                return baseTypeIsEqual(o1, o2);
            }
        } else {
            if (o1 == null && o2 == null) {
                return false;
            } else if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
                return false;
            } else {
                return baseTypeIsEqual(o1, o2);
            }
        }
    }

    public static boolean baseTypeIsEqual(Object o1, Object o2) {
        if (o1 instanceof Number && o2 instanceof Number) {
            if (o1.toString().equals((o2.toString()))) {
                return true;
            }
        }
        if (o1 instanceof Integer && o2 instanceof Integer) {
            if (((Integer) o1).intValue() == ((Integer) o2).intValue()) {
                return true;
            }
        }
        if (o1 instanceof String && o2 instanceof String) {
            if (((String) o1).equals((String) o2)) {
                return true;
            }
        }
        if (o1 instanceof Long && o2 instanceof Long) {
            if (((Long) o1).longValue() == ((Long) o2).longValue()) {
                return true;
            }
        }
        if (o1 instanceof Double && o2 instanceof Double) {
            if (((Double) o1).doubleValue() == ((Double) o2).doubleValue()) {
                return true;
            }
        }
        if (o1 instanceof Short && o2 instanceof Short) {
            if (((Short) o1).shortValue() == ((Short) o2).shortValue()) {
                return true;
            }
        }
        if (o1 instanceof Float && o2 instanceof Float) {
            if (((Float) o1).floatValue() == ((Float) o2).floatValue()) {
                return true;
            }
        }
        if (o1 instanceof Boolean && o2 instanceof Boolean) {
            if ((Boolean) o1 == false && (Boolean) o2 == false) {
                return true;
            }
            return ((Boolean) o1 && (Boolean) o2);
        }
        return false;
    }


    public static boolean objFieldsIsEqual(Object o1, Object o2, Map<String, String> fields) {
        boolean isEqual = false;
        Object v1, v2;
        String f2;
        for (String f1 : fields.keySet()) {
            f2 = fields.get(f1);
            v1 = ReflectUtil.getValByNumberVar(o1, f1);
            v2 = ReflectUtil.getValByNumberVar(o2, f2);
            isEqual = baseTypeIsEqual(v1, v2, false);
            if (!isEqual) break;
        }
        return isEqual;
    }


    public static boolean baseTypeLike(Object baseObj, Object obj) {
        if (baseObj == null || obj == null) return false;
        if (baseObj instanceof String && obj instanceof String) {
            if (((String) baseObj).contains((String) obj)) {
                return true;
            }
        }
        return false;
    }

    public static int compareTo(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null && o2 != null) return 1;
        if (o1 != null && o2 == null) return -1;
        if (o1 instanceof Integer && o2 instanceof Integer) {
            return ((Integer) o1).compareTo(((Integer) o2));
        }
        if (o1 instanceof String && o2 instanceof String) {
            return ((String) o1).compareTo(((String) o2));
        }
        if (o1 instanceof Long && o2 instanceof Long) {
            return ((Long) o1).compareTo(((Long) o2));
        }
        if (o1 instanceof Double && o2 instanceof Double) {
            return ((Double) o1).compareTo(((Double) o2));
        }
        if (o1 instanceof Short && o2 instanceof Short) {
            return ((Short) o1).compareTo(((Short) o2));
        }
        if (o1 instanceof Float && o2 instanceof Float) {
            return ((Float) o1).compareTo(((Float) o2));
        }
        if (o1 instanceof Boolean && o2 instanceof Boolean) {
            return ((Boolean) o1).compareTo(((Boolean) o2));
        }
        return 0;
    }
}
