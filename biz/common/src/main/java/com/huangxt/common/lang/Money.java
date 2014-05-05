package com.huangxt.common.lang;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * 货币及金额的类
 */
public class Money implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 2578789649867031241L;
    
    //*****************************私有和公共的常量*********************************

    /**
     * 缺省的币种代码，为CNY（人民币）。
     */
    public static final String DEFAULT_CURRENCY_CODE = "CNY";

    /**
     * 缺省的取整模式，为<code>BigDecimal.ROUND_HALF_EVEN
     * （四舍五入，当小数为0.5时，则取最近的偶数）。
     */
    public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /** 一组可能的元/分换算比例。<br>
     * 此处，“分”是指货币的最小单位，“元”是货币的最常用单位。<br>
     * 不同的币种有不同的元/分换算比例，如人民币是100，而日元为1。
     */
    private static final int[] centFactors = new int[] { 1, 10, 100, 1000 };

    /** 金额（单位为分） */
    private long cent;

    /** 币种 */
    private Currency currency;

    
    
    //************************构造器***************************************
    
    /**
     * 创建一个具有金额x元y分和缺省币种的货币对象。
     */
    public Money(long yuan, int cent){
        this(yuan, cent, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 创建一个具有金额x元y分和指定币种的货币对象。
     */
    public Money(long yuan, int cent, Currency currency){
        this.currency = currency;
        this.cent = (yuan * getCentFactor()) + (cent % getCentFactor());
    }

    /**
     * 创建一个具有金额amount元和缺省币种的货币对象。
     */
    public Money(String amount){
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 创建一个具有金额amount元和指定币种currency的货币对象。
     */
    public Money(String amount, Currency currency){
        this(new BigDecimal(new Double(amount).doubleValue()), currency);
    }

    /**
     * 创建一个具有金额amount元和指定币种currency的货币对象。<br>
     * 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     */
    public Money(String amount, Currency currency, int roundingMode){
        this(new BigDecimal(new Double(amount).doubleValue()), currency, roundingMode);
    }
    
    /**
     * 创建一个具有参数amount指定金额和缺省币种的货币对象。 如果金额不能转换为整数分，则使用四舍五入方式取整。<br>
     * 注意：由于double类型运算中存在误差，使用四舍五入方式取整的 结果并不确定，因此，应尽量避免使用double类型创建货币类型。
     */
    public Money(double amount){
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 创建一个具有金额amount和指定币种的货币对象。 如果金额不能转换为整数分，则使用四舍五入方式取整。<br>
     * 注意：由于double类型运算中存在误差，使用四舍五入方式取整的 结果并不确定，因此，应尽量避免使用double类型创建货币类型。
     */
    public Money(double amount, Currency currency){
        this.currency = currency;
        this.cent = Math.round(amount * getCentFactor());
    }
    
    /**
     * 创建一个具有金额amount和缺省币种的货币对象。 如果金额不能转换为整数分，则使用缺省取整模式DEFAULT_ROUNDING_MODE取整。
     */
    public Money(BigDecimal amount){
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 创建一个具有参数amount指定金额和缺省币种的货币对象。 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     */
    public Money(BigDecimal amount, int roundingMode){
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
    }

    /**
     * 创建一个具有金额amount和指定币种的货币对象。 如果金额不能转换为整数分，则使用缺省的取整模式DEFAULT_ROUNDING_MODE进行取整。
     */
    public Money(BigDecimal amount, Currency currency){
        this(amount, currency, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 创建一个具有金额amount和指定币种的货币对象。 如果金额不能转换为整数分，则使用指定的取整模式roundingMode取整。
     */
    public Money(BigDecimal amount, Currency currency, int roundingMode){
        this.currency = currency;
        this.cent = rounding(amount.movePointRight(currency.getDefaultFractionDigits()), roundingMode);
    }


    
    //************************setter和getter********************************
    
    /**
     * 获取本货币对象代表的金额数（以元为单位）
     */
    public BigDecimal getAmount() {
        return BigDecimal.valueOf(cent, currency.getDefaultFractionDigits());
    }

    /**
     * 设置本货币对象代表的金额数（以元为单位）
     */
    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            cent = rounding(amount.movePointRight(2), BigDecimal.ROUND_HALF_EVEN);
        }
    }

    /**
     * 获取本货币对象代表的金额数（以分为单位）
     */
    public long getCent() {
        return cent;
    }

    /**
     * 设置本货币对象代表的金额数（以分为单位）
     */
    public void setCent(long l) {
        cent = l;
    }
    
    /**
     * 获取本货币对象代表的币种。
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * 获取本货币币种的元/分换算比率。
     */
    public int getCentFactor() {
        return centFactors[currency.getDefaultFractionDigits()];
    }

    
    //************************基本对象方法********************************

    public String toString() {
        return getAmount().toString();
    }
    
    /**
     * 判断本货币对象与另一对象是否相等。<br>
     * 本货币对象与另一对象相等的充分必要条件是：<br>
     * <li>另一对象也属货币对象类。
     * <li>金额相同。
     * <li>币种相同。
     */
    public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    private boolean equals(Money other) {
        return currency.equals(other.currency) && (cent == other.cent);
    }

    /**
     * 计算本货币对象的杂凑值。
     */
    public int hashCode() {
        return (int) (cent ^ (cent >>> 32));
    }

    
    //************************Comparable接口********************************

    /**
     * 比较本对象与另一对象的大小<br>
     * 如果本货币对象的金额少于待比较货币对象，则返回-1<br>
     * 如果本货币对象的金额等于待比较货币对象，则返回0<br>
     * 如果本货币对象的金额大于待比较货币对象，则返回1<br>
     * 
     * @exception ClassCastException 待比较货币对象不是Money。
     * @exception IllegalArgumentException 待比较货币对象与本货币对象的币种不同。
     */
    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    private int compareTo(Money other) {
        assertSameCurrencyAs(other);

        if (cent < other.cent) {
            return -1;
        } else if (cent == other.cent) {
            return 0;
        } else {
            return 1;
        }
    }

    
    //************************基本算术运算********************************

    /**
     * 货币加法。<br>
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为两货币对象金额之和，本货币对象的值不变。
     * 
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    public Money add(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(cent + other.cent);
    }

    /**
     * 货币累加。<br>
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之和，并返回本货币对象的引用。
     * 
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    public Money addTo(Money other) {
        assertSameCurrencyAs(other);
        this.cent += other.cent;
        return this;
    }
    
    /**
     * 货币减法。<br>
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为本货币对象的金额减去参数货币对象的金额。本货币对象的值不变。
     * 
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    public Money subtract(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(cent - other.cent);
    }

    /**
     * 货币累减。<br>
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之差，并返回本货币对象的引用。
     * 
     * @exception IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    public Money subtractFrom(Money other) {
        assertSameCurrencyAs(other);
        this.cent -= other.cent;
        return this;
    }

    /**
     * 货币乘法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数val。 本货币对象的值不变。
     */
    public Money multiply(long val) {
        return newMoneyWithSameCurrency(cent * val);
    }

    /**
     * 货币累乘。<br>
     * 本货币对象金额乘以乘数val，并返回本货币对象。
     */
    public Money multiplyBy(long val) {
        this.cent *= val;
        return this;
    }

    /**
     * 货币乘法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数val。 本货币对象的值不变。如果相乘后的金额不能转换为整数分，则四舍五入。
     */
    public Money multiply(double val) {
        return newMoneyWithSameCurrency(Math.round(cent * val));
    }

    /**
     * 货币累乘。<br>
     * 本货币对象金额乘以乘数val，并返回本货币对象。 如果相乘后的金额不能转换为整数分，则使用四舍五入。
     */
    public Money multiplyBy(double val) {
        this.cent = Math.round(this.cent * val);
        return this;
    }

    /**
     * 货币乘法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数val。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用缺省的取整模式DEFUALT_ROUNDING_MODE进行取整。
     */
    public Money multiply(BigDecimal val) {
        return multiply(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累乘。<br>
     * 本货币对象金额乘以乘数val，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用缺省的取整方式DEFUALT_ROUNDING_MODE进行取整。
     */
    public Money multiplyBy(BigDecimal val) {
        return multiplyBy(val, DEFAULT_ROUNDING_MODE);
    }
    
    /**
     * 货币乘法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数val。<br>
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用指定的取整方式roundingMode进行取整。
     */
    public Money multiply(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);
        return newMoneyWithSameCurrency(rounding(newCent, roundingMode));
    }

    /**
     * 货币累乘。<br>
     * 本货币对象金额乘以乘数val，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用指定的取整方式roundingMode进行取整。
     */
    public Money multiplyBy(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);
        this.cent = rounding(newCent, roundingMode);
        return this;
    }
    
    /**
     * 货币除法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。<br>
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     */
    public Money divide(double val) {
        return newMoneyWithSameCurrency(Math.round(cent / val));
    }

    /**
     * 货币累除。<br>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     */
    public Money divideBy(double val) {
        this.cent = Math.round(this.cent / val);
        return this;
    }

    /**
     * 货币除法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用缺省的取整模式DEFAULT_ROUNDING_MODE进行取整。
     */
    public Money divide(BigDecimal val) {
        return divide(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币除法。<br>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用指定的取整模式roundingMode进行取整。
     */
    public Money divide(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);
        return newMoneyWithSameCurrency(newCent.longValue());
    }

    /**
     * 货币累除。<br>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用缺省的取整模式DEFAULT_ROUNDING_MODE进行取整。
     */
    public Money divideBy(BigDecimal val) {
        return divideBy(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累除。<br>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用指定的取整模式roundingMode进行取整。
     */
    public Money divideBy(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);
        this.cent = newCent.longValue();
        return this;
    }

    /**
     * 将本货币对象尽可能平均分配成targets份。 如果不能平均分配尽，则将零头放到开始的若干份中。分配运算能够确保不会丢失金额零头。<br>
     * 返回货币对象数组，数组的长度与分配份数相同，数组元素 从大到小排列，所有货币对象的金额最多只相差1分。
     */
    public Money[] allocate(int targets) {
        Money[] results = new Money[targets];

        Money lowResult = newMoneyWithSameCurrency(cent / targets);
        Money highResult = newMoneyWithSameCurrency(lowResult.cent + 1);

        int remainder = (int) cent % targets;

        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }

        for (int i = remainder; i < targets; i++) {
            results[i] = lowResult;
        }

        return results;
    }

    /**
     * 将本货币对象按照规定的比例分配成若干份。分配所剩的零头 从第一份开始顺序分配。分配运算确保不会丢失金额零头。
     * 
     * @param ratios 分配比例数组，每一个比例是一个长整型，代表相对于总数的相对数。
     * @return 货币对象数组，数组的长度与分配比例数组的长度相同。
     */
    public Money[] allocate(long[] ratios) {
        Money[] results = new Money[ratios.length];

        long total = 0;

        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }

        long remainder = cent;

        for (int i = 0; i < results.length; i++) {
            results[i] = newMoneyWithSameCurrency((cent * ratios[i]) / total);
            remainder -= results[i].cent;
        }

        for (int i = 0; i < remainder; i++) {
            results[i].cent++;
        }

        return results;
    }

    
    //***********************************内部方法***************************************

    /**
     * 断言本货币对象与另一货币对象是否具有相同的币种。<br>
     * 如果本货币对象与另一货币对象具有相同的币种，则方法返回。 否则抛出运行时异常java.lang.IllegalArgumentException
     */
    private void assertSameCurrencyAs(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Money math currency mismatch.");
        }
    }

    /**
     * 对BigDecimal型的值val按指定取整方式roundingMode取整。
     */
    private long rounding(BigDecimal val, int roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }

    /**
     * 创建一个币种相同，具有指定金额(以分为单位)的货币对象。
     */
    private Money newMoneyWithSameCurrency(long cent) {
        Money money = new Money(0, currency);
        money.cent = cent;
        return money;
    }
}