package com.doordash.lite;

import com.doordash.utils.CommonUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by amaheshwari on 9/30/2018.
 */

public class CommonUtilsTest {

    @Test
    public void testConvertCentsToDollarPositive() throws Exception {

        int centsInput = 200;
        double expectedResult = 2;

        Assert.assertEquals("Conversion cents to dollar failed", expectedResult,
                CommonUtils.convertCentsToDollar(centsInput), 0);
    }

    @Test
    public void testConvertCentsToDollarNegative() throws Exception {

        int centsInput = -200;
        double expectedResult = 0;

        Assert.assertEquals("Conversion cents to dollar failed", expectedResult,
                CommonUtils.convertCentsToDollar(centsInput), 0);
    }

    @Test
    public void testIsNullOrEmptyStringPositive() throws Exception {
        Assert.assertTrue("String should be null or empty", CommonUtils.isNullOrEmpty(null));
    }

    @Test
    public void testIsNullOrEmptyStringNegative() throws Exception {
        Assert.assertFalse("String should not be null or empty", CommonUtils.isNullOrEmpty("test"));
    }
}
