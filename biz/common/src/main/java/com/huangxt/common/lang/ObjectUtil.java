package com.huangxt.common.lang;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 有关Object处理的工具类，这个类中的每个方法都可以安全地处理null
 */
public class ObjectUtil {

    //XXX:常量
    /**
     * 用于表示null的常量。
     * 
     * <p>
     * 例如，HashMap.get(Object)方法返回null有两种可能：
     * 值不存在或值为null。而这个singleton可用来区别这两种情形。
     * </p>
     * 
     * <p>
     * 另一个例子是，Hashtable的值不能为null。
     * </p>
     */
    public static final Object NULL = new Serializable() {
        private static final long serialVersionUID = 7092611880189329093L;

        private Object readResolve() {
            return NULL;
        }
    };

    //XXX:默认值函数。

    /**
     * 如果对象为null，则返回指定默认对象，否则返回对象本身。
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     */
    public static Object defaultIfNull(Object object, Object defaultValue) {
        return (object != null) ? object : defaultValue;
    }

    //XXX:比较函数。

    /**
     * 比较两个对象是否完全相等。此方法可以正确地比较多维数组。
     * <pre>
     * ObjectUtil.equals(null, null)                  = true
     * ObjectUtil.equals(null, "")                    = false
     * ObjectUtil.equals("", null)                    = false
     * ObjectUtil.equals("", "")                      = true
     * ObjectUtil.equals(Boolean.TRUE, null)          = false
     * ObjectUtil.equals(Boolean.TRUE, "true")        = false
     * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     */
    public static boolean equals(Object object1, Object object2) {
        return ArrayUtil.equals(object1, object2);
    }

    //XXX:Hashcode函数。
    
    /**
     * 取得对象的hash值, 如果对象为null, 则返回0。此方法可以正确地处理多维数组。
     */
    public static int hashCode(Object object) {
        return ArrayUtil.hashCode(object);
    }

    /**
     * 取得对象的原始的hash值, 如果对象为null, 则返回0。
     * <p>
     * 该方法使用System.identityHashCode来取得hash值，该值不受对象本身的hashCode方法的影响。
     * </p>
     */
    public static int identityHashCode(Object object) {
        return (object == null) ? 0 : System.identityHashCode(object);
    }

    //XXX:取得对象的identity。

    /**
     * 取得对象自身的identity，如同对象没有覆盖toString()方法时，Object.toString()的原始输出。
     * <pre>
     * ObjectUtil.identityToString(null, "NULL")            = "NULL"
     * ObjectUtil.identityToString("", "NULL")              = "java.lang.String@1e23"
     * ObjectUtil.identityToString(Boolean.TRUE, "NULL")    = "java.lang.Boolean@7fa"
     * ObjectUtil.identityToString(new int[0], "NULL")      = "int[]@7fa"
     * ObjectUtil.identityToString(new Object[0], "NULL")   = "java.lang.Object[]@7fa"
     * </pre>
     *
     * @param object 对象
     * @param nullStr 如果对象为null，则返回该字符串
     *
     * @return 对象的identity，如果对象是null，则返回指定字符串
     */
    public static String identityToString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        }
        return appendIdentityToString(null, object).toString();
    }

    /**
     * 将对象自身的identity——如同对象没有覆盖toString()方法时，Object.toString()的原始输出——追加到StringBuffer中。
     * <pre>
     * ObjectUtil.appendIdentityToString(*, null)            = null
     * ObjectUtil.appendIdentityToString(null, "")           = "java.lang.String@1e23"
     * ObjectUtil.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
     * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append("int[]@7fa")
     * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append("java.lang.Object[]@7fa")
     * </pre>
     *
     * @param buffer StringBuffer对象，如果是null，则创建新的
     * @param object 对象
     *
     * @return StringBuffer对象，如果对象为null，则返回null
     */
    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            return null;
        }

        if (buffer == null) {
            buffer = new StringBuffer();
        }

        buffer.append(ClassUtil.getClassNameForObject(object));

        return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
    }

    //XXX:Clone函数。

    /**
     * 复制一个对象。如果对象为null，则返回null。此方法调用Object.clone方法，默认只进行“浅复制”。 对于数组，则会调用ArrayUtil.clone方法（更高效）。
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object clone(Object array) {
        if (array == null) {
            return null;
        }

        // 对数组特殊处理
        if (array instanceof Object[]) {
            return ArrayUtil.clone((Object[]) array);
        }

        if (array instanceof long[]) {
            return ArrayUtil.clone((long[]) array);
        }

        if (array instanceof int[]) {
            return ArrayUtil.clone((int[]) array);
        }

        if (array instanceof short[]) {
            return ArrayUtil.clone((short[]) array);
        }

        if (array instanceof byte[]) {
            return ArrayUtil.clone((byte[]) array);
        }

        if (array instanceof double[]) {
            return ArrayUtil.clone((double[]) array);
        }

        if (array instanceof float[]) {
            return ArrayUtil.clone((float[]) array);
        }

        if (array instanceof boolean[]) {
            return ArrayUtil.clone((boolean[]) array);
        }

        if (array instanceof char[]) {
            return ArrayUtil.clone((char[]) array);
        }

        // Not cloneable
        if (!(array instanceof Cloneable)) {
            throw new RuntimeException("Object of class " + array.getClass().getName() + " is not Cloneable");
        }

        // 用reflection调用clone方法
        Class clazz = array.getClass();

        try {
            Method cloneMethod = clazz.getMethod("clone", ArrayUtil.EMPTY_CLASS_ARRAY);
            return cloneMethod.invoke(array, ArrayUtil.EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //XXX:比较对象的类型。

    /**
     * 检查两个对象是否属于相同类型。null将被看作任意类型。
     */
    public static boolean isSameType(Object object1, Object object2) {
        if ((object1 == null) || (object2 == null)) {
            return true;
        }
        return object1.getClass().equals(object2.getClass());
    }

    //XXX:toString方法。

    /**
     * 取得对象的toString()的值，如果对象为null，则返回空字符串""。
     * <pre>
     * ObjectUtil.toString(null)         = ""
     * ObjectUtil.toString("")           = ""
     * ObjectUtil.toString("bat")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE) = "true"
     * ObjectUtil.toString([1, 2, 3])    = "[1, 2, 3]"
     * </pre>
     */
    public static String toString(Object object) {
        return (object == null) ? StringUtil.EMPTY_STRING
                                : (object.getClass().isArray() ? ArrayUtil.toString(object)
                                                               : object.toString());
    }

    /**
     * 取得对象的toString()的值，如果对象为null，则返回指定字符串。
     * <pre>
     * ObjectUtil.toString(null, null)           = null
     * ObjectUtil.toString(null, "null")         = "null"
     * ObjectUtil.toString("", "null")           = ""
     * ObjectUtil.toString("bat", "null")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE, "null") = "true"
     * ObjectUtil.toString([1, 2, 3], "null")    = "[1, 2, 3]"
     * </pre>
     */
    public static String toString(Object object, String nullStr) {
        return (object == null) ? nullStr
                                : (object.getClass().isArray() ? ArrayUtil.toString(object)
                                                               : object.toString());
    }
}
