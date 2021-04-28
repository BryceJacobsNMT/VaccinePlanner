package edu.nmt.util;


/**
 * A collection of useful methods that act on generic Objects.
 */
public class ObjectUtility {
    
    public final static String DEFAULT_NAME = "Unnamed";

    /**
     * Constructor.
     */
    private ObjectUtility() {
    }

    /**
     * Calls the .equals() method on thisOne, but also takes into account the
     * possibility one or both arguments are null. If both are null, this method
     * returns true.
     *
     * @param thisOne - the first comparison object.
     * @param thatOne - the second comparison object.
     * @return - true if the objects are equal; false otherwise.
     */
    public static boolean objectsAreEqual(Object thisOne, Object thatOne) {
        return (thisOne == null) ? (thatOne == null) : thisOne.equals(thatOne);
    }

    /**
     * Returns the hash code of the parameter. If <tt>o</tt> is <i>null</i> zero
     * is returned.
     *
     * @param o - the object for which a hash code is needed.
     * @return - the hash of the parameter.
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * Parses a string representation of a hash map into a list of strings in the
     * format key=value.
     * @param str - string representation of a hash map.
     * @return - a list of key=value pairs.
     */
    public static String[] mapParse(String str) {
        str = str.replaceAll("\\{", "");
        str = str.replaceAll("\\}", "");
        String[] pairs = str.split(",");
        return pairs;
    }
}
